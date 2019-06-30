package com.github.thibseisel.sfyxplor

import com.github.thibseisel.api.spotify.AuthToken
import io.kotlintest.*
import io.kotlintest.matchers.collections.*
import io.kotlintest.matchers.withClue
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.*
import kotlinx.coroutines.test.runBlockingTest
import org.intellij.lang.annotations.Language
import kotlin.test.Test

private const val TEST_CLIENT_ID = "client_id"
private const val TEST_CLIENT_SECRET = "client_secret"
private const val CLIENT_BASE64_KEY = "Y2xpZW50X2lkOmNsaWVudF9zZWNyZXQ="

class SpotifyApiClientTest {
    
    @Test
    fun givenNoAuthToken_whenRequestingAny_thenFailWithAuthRequired() = runBlockingTest {
        val unauthenticatedEngine = MockEngine { request ->
            val authorizationHeader = request.headers[HttpHeaders.Authorization]
            if (authorizationHeader == null) {
                respondJsonError(HttpStatusCode.Unauthorized, "No token provided")
            } else {
                fail("Expected to have no Authorization header, but was \"$authorizationHeader\"")
            }
        }

        val apiClient = SpotifyApiClientImpl(unauthenticatedEngine)
        shouldThrow<AuthenticationRequired> {
            apiClient.getTrack("abcde")
        }
    }

    @Test
    fun givenExpiredToken_whenRequestingAny_thenFailWithAuthRequired() = runBlockingTest {
        val expiredToken = AuthToken("expired_token", 0)

        val givenRejectingTokenEngine = MockEngine { request ->
            val passedToken = request.headers[HttpHeaders.Authorization]
                ?.substringAfter("Bearer ", "")
                ?.takeUnless { it.isEmpty() }
                ?: fail("Expected an Authorization header, but was missing or malformed.")

            if (passedToken == expiredToken.token) {
                respondJsonError(HttpStatusCode.Unauthorized, "The access token expired")
            } else {
                fail("Expected to receive the expired token, but was \"$passedToken\"")
            }
        }

        val apiClient = SpotifyApiClientImpl(givenRejectingTokenEngine, expiredToken)
        shouldThrow<AuthenticationRequired> {
            apiClient.getTrack("abcde")
        }
    }

    @Test
    fun givenCredentials_whenAuthenticating_thenSendThemToSpotifyAuthServiceAsBase64() = runBlockingTest {
        val authEngine = MockEngine { request ->
            request.method shouldBe HttpMethod.Post
            request.url.host shouldBe "accounts.spotify.com"
            request.url.encodedPath shouldBe "/api/token"
            request.url.parameters["grant_type"] shouldBe "client_credentials"
            request.headers[HttpHeaders.Authorization] shouldBe "Basic $CLIENT_BASE64_KEY"

            respondJson("""{
                "access_token": "valid_access_token",
                "token_type": "bearer",
                "expires_in": 3600
            }""".trimIndent())
        }

        val apiClient = SpotifyApiClientImpl(authEngine)
        val token = apiClient.authenticate(TEST_CLIENT_ID, TEST_CLIENT_SECRET)

        token.token shouldBe "valid_access_token"
        token.expiresIn shouldBe 3600
    }

    @Test
    fun givenBadCredentials_whenAuthenticating_thenFailWithAuthException() = runBlockingTest {
        val authFailureEngine = MockEngine { respondError(HttpStatusCode.Unauthorized) }
        val apiClient = SpotifyApiClientImpl(authFailureEngine)

        shouldThrow<AuthenticationException> {
            apiClient.authenticate(TEST_CLIENT_ID, "bad_client_secret")
        }
    }

    @Test
    fun whenGettingAnArtist_thenCallArtistEndpointWithItsId() = runBlockingTest {
        val artistEndpoint = MockEngine { request ->
            request.method shouldBe HttpMethod.Get
            request.url.host shouldBe "api.spotify.com"
            request.url.encodedPath shouldBe "/v1/artists/12Chz98pHFMPJEknJQMWvI"

            respondJson(SINGLE_ARTIST)
        }

        val apiClient = SpotifyApiClientImpl(artistEndpoint)
        val artist = apiClient.getArtist("12Chz98pHFMPJEknJQMWvI")

        artist.id shouldBe "12Chz98pHFMPJEknJQMWvI"
        artist.name shouldBe "Muse"
        artist.popularity shouldBe 82
        artist.genres.shouldContainExactlyInAnyOrder("modern rock", "permanent wave", "piano rock", "post-grunge", "rock")
        artist.images.shouldHaveSize(3)
    }

    @Test
    fun whenGettingMultipleArtists_thenCallArtistEndpointWithTheirIds() = runBlockingTest {
        val requestedArtistIds = listOf("12Chz98pHFMPJEknJQMWvI", "C7jy3rLJdDQY21OgRLCZ9sD")

        val severalArtistsEndpoint = MockEngine { request ->
            request.method shouldBe HttpMethod.Get
            request.url.host shouldBe "api.spotify.com"
            request.url.encodedPath shouldBe "/v1/artists"

            val requestedIds = request.url.parameters.getAll("ids")
            requestedIds.shouldContainExactly(requestedArtistIds)

            respondJson(MULTIPLE_ARTISTS)
        }

        val apiClient = SpotifyApiClientImpl(severalArtistsEndpoint)
        val artists = apiClient.getSeveralArtists(requestedArtistIds)

        artists shouldHaveSize 2
        artists[0].id shouldBe "12Chz98pHFMPJEknJQMWvI"
        artists[1].id shouldBe "C7jy3rLJdDQY21OgRLCZ9sD"
    }

    @Test
    fun whenGettingAnArtistAlbums_thenCallArtistAlbumsEndpointWithItsId() = runBlockingTest {
        val artistAlbumsEndpoint = MockEngine { request ->
            request.method shouldBe HttpMethod.Get
            request.url.host shouldBe "api.spotify.com"
            request.url.encodedPath shouldBe "/v1/artists/12Chz98pHFMPJEknJQMWvI/albums"

            withClue("Only albums and singles from that artist should be requested.") {
                val includedGroups = request.url.parameters.getAll("include_groups")
                includedGroups.shouldContainExactlyInAnyOrder("album", "single")
            }

            respondJson(ARTIST_ALBUMS)
        }

        val apiClient = SpotifyApiClientImpl(artistAlbumsEndpoint)
        val paginatedAlbums = apiClient.getArtistAlbums("12Chz98pHFMPJEknJQMWvI")

        paginatedAlbums.total shouldBe 46
        val items = paginatedAlbums.items
        items shouldHaveSize 2

        items[0].should {
            it.id shouldBe "5OZgDtx180ZZPMpm36J2zC"
            it.name shouldBe "Simulation Theory (Super Deluxe)"
            it.releaseDate shouldBe "2018-11-09"
            it.releaseDatePrecision shouldBe "day"
            it.images shouldHaveSize 3
        }

        items[1].should {
            it.id shouldBe "2wart5Qjnvx1fd7LPdQxgJ"
            it.name shouldBe "Drones"
            it.releaseDate shouldBe "2015-06-04"
            it.releaseDatePrecision shouldBe "day"
            it.images shouldHaveSize 3
        }
    }
}

private fun respondJson(@Language("JSON") json: String, status: HttpStatusCode = HttpStatusCode.OK) = respond(
    json, status, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
)

private fun respondJsonError(status: HttpStatusCode, message: String = status.description) = respondJson(
    """{ 
        "status": ${status.value},
        "message": "$message"
    }""".trimIndent(),
    status
)
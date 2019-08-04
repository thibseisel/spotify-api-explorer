package com.github.thibseisel.api.spotify

import io.kotlintest.*
import io.kotlintest.matchers.*
import io.kotlintest.matchers.collections.*
import io.kotlintest.matchers.numerics.*
import io.kotlintest.matchers.types.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.intellij.lang.annotations.*
import java.util.*
import kotlin.test.*

private const val TEST_CLIENT_ID = "client_id"
private const val TEST_CLIENT_SECRET = "client_secret"
private const val CLIENT_BASE64_KEY = "Y2xpZW50X2lkOmNsaWVudF9zZWNyZXQ="
private const val TEST_USER_AGENT = "SpotifyApiClient/1.0.0 KtorHttpClient/1.2.2"

/**
 * Create an instance of the [SpotifyApiClient] under test.
 *
 * @param engine The mock engine, used to simulate the response of the remote server.
 * @param providedToken An optional access token to provided authentication at construction.
 *
 * @return An instance of [SpotifyApiClient] to be tested.
 * The returned client will use [TEST_USER_AGENT] as its user-agent.
 */
private fun spotifyClient(
    engine: MockEngine,
    providedToken: OAuthToken? = null
) = SpotifyApiClientImpl(engine, TEST_USER_AGENT, providedToken)

/**
 * Create an instance of the [SpotifyApiClient] under test.
 *
 * @param providedToken An optional access token to provided authentication at construction.
 * @param mockHandler A function that simulates the behavior of the remote server,
 * mapping the sent request to an arbitrary response.
 *
 * @return An instance of [SpotifyApiClient] to be tested.
 * The returned client will use [TEST_USER_AGENT] as its user-agent.
 */
private fun spotifyClient(
    providedToken: OAuthToken? = null,
    mockHandler: suspend (HttpRequestData) -> HttpResponseData
): SpotifyApiClient {
    val simulatedServer = MockEngine(mockHandler)
    return SpotifyApiClientImpl(simulatedServer, TEST_USER_AGENT, providedToken)
}

/**
 * Test specification for [SpotifyApiClient].
 */
@ExperimentalCoroutinesApi
class SpotifyApiClientTest {
    
    @Test
    fun `Given no access token, when calling any endpoint then fail by requiring authentication`() = runBlockingTest {
        val unauthenticatedClient = spotifyClient { request ->
            val authorizationHeader = request.headers[HttpHeaders.Authorization]
            if (authorizationHeader == null) {
                respondJsonError(HttpStatusCode.Unauthorized, "No token provided")
            } else {
                fail("Expected to have no Authorization header, but was \"$authorizationHeader\"")
            }
        }

        shouldThrow<AuthenticationRequired> {
            unauthenticatedClient.getTrack("7f0vVL3xi4i78Rv5Ptn2s1")
        }
    }

    @Test
    fun `Given an expired token, when calling any endpoint then fail by requiring authentication`() = runBlockingTest {
        val expiredToken = OAuthToken(TEST_TOKEN_STRING, 0)

        val expiredTokenClient = spotifyClient(expiredToken) { request ->
            val passedToken = request.headers[HttpHeaders.Authorization]
                ?.substringAfter("Bearer ", "")
                ?.takeUnless { it.isEmpty() }
                ?: fail("Expected an Authorization header, but was missing or malformed.")

            if (passedToken == expiredToken.token) {
                respondJsonError(
                    HttpStatusCode.Unauthorized,
                    "The access token expired"
                )
            } else {
                fail("Expected to receive the expired token, but was \"$passedToken\"")
            }
        }

        shouldThrow<AuthenticationRequired> {
            expiredTokenClient.getTrack("7f0vVL3xi4i78Rv5Ptn2s1")
        }
    }

    @Test
    fun `When authenticating and authentication failed, then fail with AuthenticationException`() = runBlockingTest {
        val authFailureClient = spotifyClient {
            respondJsonError(HttpStatusCode.BadRequest)
        }

        shouldThrow<AuthenticationException> {
            authFailureClient.authenticate(TEST_CLIENT_ID, TEST_CLIENT_SECRET)
        }
    }

    @Test
    fun `Given valid credentials, when authenticating then send them to Spotify Accounts service as Base64`() = runBlockingTest {
        val unauthenticatedClient = spotifyClient { request ->
            request.method shouldBe HttpMethod.Post
            request.url.host shouldBe "accounts.spotify.com"
            request.url.encodedPath shouldBe "api/token"
            request.headers[HttpHeaders.Authorization] shouldBe "Basic $CLIENT_BASE64_KEY"

            request.body.shouldBeInstanceOf<FormDataContent> {
                it.formData["grant_type"] shouldBe "client_credentials"
            }

            respondJson(AUTH_TOKEN)
        }

        val token = unauthenticatedClient.authenticate(
            TEST_CLIENT_ID,
            TEST_CLIENT_SECRET
        )

        token.token shouldBe TEST_TOKEN_STRING
        token.expiresIn shouldBe 3600
    }

    @Test
    fun `Given authentication, when calling any endpoint, then send token as Authorization`() = runBlockingTest {
        val unauthenticatedClient = spotifyClient { request ->
            when (request.url.host) {
                "accounts.spotify.com" -> respondJson(AUTH_TOKEN)
                "api.spotify.com" -> {
                    request.headers[HttpHeaders.Authorization] shouldBe "Bearer $TEST_TOKEN_STRING"
                    respondJson(SINGLE_ARTIST)
                }
                else -> fail("Unexpected request url: ${request.url}")
            }
        }

        unauthenticatedClient.authenticate(
            TEST_CLIENT_ID,
            TEST_CLIENT_SECRET
        )
        unauthenticatedClient.getArtist("12Chz98pHFMPJEknJQMWvI")
    }

    @Test
    fun `When calling any endpoint while reaching rate limit, then retry after specified delay`() = runBlockingTest {
        val rateLimitedEngine = givenReachedRateLimit(retryAfter = 4)

        val apiClient = spotifyClient(rateLimitedEngine)
        apiClient.getArtist("12Chz98pHFMPJEknJQMWvI")

        withClue("Client should wait at least the given Retry-After time before re-issuing the request") {
            currentTime shouldBeGreaterThanOrEqual 4000L
        }
    }

    @Test
    fun `Given bad credentials, when authenticating then fail with AuthenticationException`() = runBlockingTest {
        val apiClient = spotifyClient { respondError(HttpStatusCode.Unauthorized) }

        shouldThrow<AuthenticationException> {
            apiClient.authenticate(TEST_CLIENT_ID, "bad_client_secret")
        }
    }

    @Test
    fun `When getting an artist then call artists endpoint with its id`() = runBlockingTest {
        val apiClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/artists/12Chz98pHFMPJEknJQMWvI"
            respondJson(SINGLE_ARTIST)
        }

        val artist = apiClient.getArtist("12Chz98pHFMPJEknJQMWvI")

        artist.id shouldBe "12Chz98pHFMPJEknJQMWvI"
        artist.name shouldBe "Muse"
        artist.popularity shouldBe 82
        artist.genres.shouldContainExactlyInAnyOrder("modern rock", "permanent wave", "piano rock", "post-grunge", "rock")

        artist.images shouldHaveSize 1
        artist.images[0].should {
            it.width shouldBe 320
            it.height shouldBe 320
            it.url shouldBe "https://i.scdn.co/image/17f00ec7613d733f2dd88de8f2c1628ea5f9adde"
        }
    }

    @Test
    fun `When getting an unknown artist then fail with ResourceNotFound`() = runBlockingTest {
        val apiClient = spotifyClient {
            respondJsonError(
                HttpStatusCode.NotFound,
                "non existing id"
            )
        }
        shouldThrow<ResourceNotFound> {
            apiClient.getArtist("non_existing_artist_id")
        }
    }

    @Test
    fun `When getting multiple artists then call artists endpoint with their ids`() = runBlockingTest {
        val requestedArtistIds = listOf("12Chz98pHFMPJEknJQMWvI", "7jy3rLJdDQY21OgRLCZ9sD")

        val apiClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/artists"

            val requestedIds =
                request.url.parameters.getAll(SpotifyApiClient.QUERY_IDS)
            requestedIds.shouldContainExactly(requestedArtistIds)

            respondJson(MULTIPLE_ARTISTS)
        }

        val artists = apiClient.getSeveralArtists(requestedArtistIds)

        artists shouldHaveSize 2
        artists[0].should {
            it.shouldNotBeNull()
            it.id shouldBe "12Chz98pHFMPJEknJQMWvI"
            it.name shouldBe "Muse"
            it.popularity shouldBe 82
            it.genres.shouldContainExactly("modern rock", "permanent wave", "piano rock", "post-grunge", "rock")
            it.images shouldHaveSize 1
        }

        artists[1].should {
            it.shouldNotBeNull()
            it.id shouldBe "7jy3rLJdDQY21OgRLCZ9sD"
            it.name shouldBe "Foo Fighters"
            it.popularity shouldBe 82
            it.genres.shouldContainExactly("alternative metal", "alternative rock", "modern rock", "permanent wave", "post-grunge", "rock")
            it.images shouldHaveSize 1
        }
    }

    @Test
    fun `When getting an artist's albums, then call artist albums endpoint with its id`() = runBlockingTest {
        val apiClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/artists/12Chz98pHFMPJEknJQMWvI/albums"

            withClue("Only albums and singles from that artist should be requested.") {
                val includedGroups =
                    request.url.parameters.getAll(SpotifyApiClient.QUERY_INCLUDE_GROUPS)
                includedGroups.shouldContainExactlyInAnyOrder("album", "single")
            }

            respondJson(ARTIST_ALBUMS)
        }

        val paginatedAlbums = apiClient.getArtistAlbums("12Chz98pHFMPJEknJQMWvI", 20, 0)

        paginatedAlbums.total shouldBe 46
        val items = paginatedAlbums.items
        items shouldHaveSize 2

        items[0].should {
            it.id shouldBe "5OZgDtx180ZZPMpm36J2zC"
            it.name shouldBe "Simulation Theory (Super Deluxe)"
            it.releaseDate shouldBe "2018-11-09"
            it.releaseDatePrecision shouldBe "day"
            it.images shouldHaveSize 1
        }

        items[1].should {
            it.id shouldBe "2wart5Qjnvx1fd7LPdQxgJ"
            it.name shouldBe "Drones"
            it.releaseDate shouldBe "2015-06-04"
            it.releaseDatePrecision shouldBe "day"
            it.images shouldHaveSize 1
        }
    }

    @Test
    fun `When getting albums of an unknown artist, then fail with ResourceNotFound`() = runBlockingTest {
        val apiClient = spotifyClient {
            respondJsonError(
                HttpStatusCode.NotFound,
                "non existing id"
            )
        }
        shouldThrow<ResourceNotFound> { apiClient.getArtistAlbums("unknown_artist_id", 20, 0) }
    }

    @Test
    fun `When getting an album then call albums endpoint with its id`() = runBlockingTest {
        val apiClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/albums/6KMkuqIwKkwUhUYRPL6dUc"
            respondJson(SINGLE_ALBUM)
        }

        val album = apiClient.getAlbum("6KMkuqIwKkwUhUYRPL6dUc")

        album.id shouldBe "6KMkuqIwKkwUhUYRPL6dUc"
        album.name shouldBe "Concrete and Gold"
        album.releaseDate shouldBe "2017-09-15"
        album.releaseDatePrecision shouldBe "day"
        album.images shouldHaveSize 1
    }

    @Test
    fun `When getting an unknown album then fail with ResourceNotFound`() = runBlockingTest {
        val apiClient = spotifyClient {
            respondJsonError(
                HttpStatusCode.NotFound,
                "non existing id"
            )
        }
        shouldThrow<ResourceNotFound> { apiClient.getAlbum("unknown_album_id") }
    }

    @Test
    fun `When getting multiple albums then call albums endpoint with their ids`() = runBlockingTest {
        val requestedAlbumIds = listOf("5OZgDtx180ZZPMpm36J2zC", "6KMkuqIwKkwUhUYRPL6dUc")

        val apiClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/albums"

            val receivedIds =
                request.url.parameters.getAll(SpotifyApiClient.QUERY_IDS)
            receivedIds shouldContainExactly requestedAlbumIds

            respondJson(MULTIPLE_ALBUMS)
        }

        val albums = apiClient.getSeveralAlbums(requestedAlbumIds)

        albums shouldHaveSize 2

        albums[0].should {
            it.shouldNotBeNull()
            it.id shouldBe "5OZgDtx180ZZPMpm36J2zC"
            it.name shouldBe "Simulation Theory (Super Deluxe)"
            it.releaseDate shouldBe "2018-11-09"
            it.releaseDatePrecision shouldBe "day"
            it.images shouldHaveSize 1
        }

        albums[1].should {
            it.shouldNotBeNull()
            it.id shouldBe "6KMkuqIwKkwUhUYRPL6dUc"
            it.name shouldBe "Concrete and Gold"
            it.releaseDate shouldBe "2017-09-15"
            it.releaseDatePrecision shouldBe "day"
            it.images shouldHaveSize 1
        }
    }

    @Test
    fun `When getting an album's tracks then call album tracks endpoint with its id`() = runBlockingTest {
        val apiClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/albums/5OZgDtx180ZZPMpm36J2zC/tracks"
            respondJson(ALBUM_TRACKS)
        }

        val paginatedTracks = apiClient.getAlbumTracks("5OZgDtx180ZZPMpm36J2zC", 20, 0)

        paginatedTracks.total shouldBe 21
        paginatedTracks.items shouldHaveSize 2

        paginatedTracks.items[0].should {
            it.id shouldBe "7f0vVL3xi4i78Rv5Ptn2s1"
            it.name shouldBe "Algorithm"
            it.discNumber shouldBe 1
            it.trackNumber shouldBe 1
            it.duration shouldBe 245960
            it.explicit shouldBe false
        }

        paginatedTracks.items[1].should {
            it.id shouldBe "0dMYPDqcI4ca4cjqlmp9mE"
            it.name shouldBe "The Dark Side"
            it.discNumber shouldBe 1
            it.trackNumber shouldBe 2
            it.duration shouldBe 227213
            it.explicit shouldBe false
        }
    }

    @Test
    fun `When getting tracks of an unknown album then fail with ResourceNotFound`() = runBlockingTest {
        val apiClient = spotifyClient {
            respondJsonError(
                HttpStatusCode.NotFound,
                "non existing id"
            )
        }
        shouldThrow<ResourceNotFound> { apiClient.getAlbumTracks("unknown_album_id", 20, 0) }
    }

    @Test
    fun `When getting track detail then call tracks endpoint with its id`() = runBlockingTest {
        val requestedTrackId = "7f0vVL3xi4i78Rv5Ptn2s1"

        val apiClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/tracks/$requestedTrackId"
            respondJson(SINGLE_TRACK)
        }

        val track = apiClient.getTrack(requestedTrackId)

        track.id shouldBe requestedTrackId
        track.name shouldBe "Algorithm"
        track.discNumber shouldBe 1
        track.trackNumber shouldBe 1
        track.explicit shouldBe false
    }

    @Test
    fun `When getting an unknown track then fail with ResourceNotFound`() = runBlockingTest {
        val apiClient = spotifyClient {
            respondJsonError(
                HttpStatusCode.NotFound,
                "non existing id"
            )
        }
        shouldThrow<ResourceNotFound> { apiClient.getTrack("unknown_track_id") }
    }

    @Test
    fun `When getting multiple tracks then call tracks endpoint with their ids`() = runBlockingTest {
        val requestedTrackIds = listOf("7f0vVL3xi4i78Rv5Ptn2s1", "0dMYPDqcI4ca4cjqlmp9mE")

        val apiClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/tracks"

            val receivedTrackIds =
                request.url.parameters.getAll(SpotifyApiClient.QUERY_IDS)
            receivedTrackIds.shouldContainExactly(requestedTrackIds)

            respondJson(MULTIPLE_TRACKS)
        }

        val tracks = apiClient.getSeveralTracks(requestedTrackIds)

        tracks shouldHaveSize 2
        tracks[0].should {
            it.shouldNotBeNull()
            it.id shouldBe "7f0vVL3xi4i78Rv5Ptn2s1"
            it.name shouldBe "Algorithm"
            it.discNumber shouldBe 1
            it.trackNumber shouldBe 1
            it.duration shouldBe 245960
            it.explicit shouldBe false
        }

        tracks[1].should {
            it.shouldNotBeNull()
            it.id shouldBe "0dMYPDqcI4ca4cjqlmp9mE"
            it.name shouldBe "The Dark Side"
            it.discNumber shouldBe 1
            it.trackNumber shouldBe 2
            it.duration shouldBe 227213
            it.explicit shouldBe false
        }
    }

    @Test
    fun `When getting features of a track then call audio-features endpoint with that track's id`() = runBlockingTest {
        val requestedTrackId = "7f0vVL3xi4i78Rv5Ptn2s1"

        val apiClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/audio-features/$requestedTrackId"
            respondJson(SINGLE_AUDIO_FEATURES)
        }

        val audioFeatures = apiClient.getTrackFeatures(requestedTrackId)

        audioFeatures.id shouldBe requestedTrackId
        audioFeatures.mode shouldBe MusicalMode.MAJOR
        audioFeatures.key shouldBe Pitch.D
        audioFeatures.tempo shouldBe 170.057f
        audioFeatures.signature shouldBe 4
        audioFeatures.loudness shouldBe -4.56f
        audioFeatures.energy shouldBe 0.923f
        audioFeatures.danceability shouldBe 0.522f
        audioFeatures.instrumentalness shouldBe 0.017f
        audioFeatures.speechiness shouldBe 0.0539f
        audioFeatures.acousticness shouldBe 0.0125f
        audioFeatures.liveness shouldBe 0.0854f
        audioFeatures.valence shouldBe 0.595f
    }

    @Test
    fun `When getting features of an unknown track then fail with ResourceNotFound`() = runBlockingTest {
        val apiClient = spotifyClient {
            respondJsonError(
                HttpStatusCode.NotFound,
                "non existing id"
            )
        }
        shouldThrow<ResourceNotFound> {
            apiClient.getTrackFeatures("unknown_track_id")
        }
    }

    @Test
    fun `When getting multiple tracks' features then call audio-features endpoint with their ids`() = runBlockingTest {
        val requestedIds = listOf("7f0vVL3xi4i78Rv5Ptn2s1", "5lnsL7pCg0fQKcWnlkD1F0")

        val apiClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/audio-features"

            val receivedTrackIds =
                request.url.parameters.getAll(SpotifyApiClient.QUERY_IDS)
            receivedTrackIds shouldContainExactly requestedIds

            respondJson(MULTIPLE_AUDIO_FEATURES)
        }

        val multipleAudioFeatures = apiClient.getSeveralTrackFeatures(requestedIds)

        multipleAudioFeatures shouldHaveSize 2

        multipleAudioFeatures[0].should {
            it.shouldNotBeNull()
            it.id shouldBe "7f0vVL3xi4i78Rv5Ptn2s1"
            it.mode shouldBe MusicalMode.MAJOR
            it.key shouldBe Pitch.D
            it.tempo shouldBe 170.057f
            it.signature shouldBe 4
            it.loudness shouldBe -4.56f
            it.energy shouldBe 0.923f
            it.danceability shouldBe 0.522f
            it.instrumentalness shouldBe 0.017f
            it.speechiness shouldBe 0.0539f
            it.acousticness shouldBe 0.0125f
            it.liveness shouldBe 0.0854f
            it.valence shouldBe 0.595f
        }

        multipleAudioFeatures[1].should {
            it.shouldNotBeNull()
            it.id shouldBe "5lnsL7pCg0fQKcWnlkD1F0"
            it.mode shouldBe MusicalMode.MAJOR
            it.key shouldBe Pitch.G
            it.tempo shouldBe 142.684f
            it.signature shouldBe 4
            it.loudness shouldBe -8.245f
            it.energy shouldBe 0.631f
            it.danceability shouldBe 0.324f
            it.instrumentalness shouldBe 0.0459f
            it.speechiness shouldBe 0.0407f
            it.acousticness shouldBe 0.00365f
            it.liveness shouldBe 0.221f
            it.valence shouldBe 0.346f
        }
    }

    @Test
    fun `When searching, then call search endpoint with corresponding type`() = runBlockingTest {
        val searchClient = spotifyClient { request ->
            request shouldGetOnSpotifyEndpoint "v1/search"

            request.url.parameters[SpotifyApiClient.QUERY_Q] shouldBe "rammstein"
            val receivedSearchTypes =
                request.url.parameters.getAll(SpotifyApiClient.QUERY_TYPE)
            receivedSearchTypes.shouldContainExactlyInAnyOrder("track", "album", "artist")

            respondJson(SEARCH_RESULTS)
        }

        val results = searchClient.search("rammstein", EnumSet.allOf(SearchType::class.java))
        results.albums.should { paginatedAlbums ->
            paginatedAlbums.total shouldBe 1
            paginatedAlbums.items shouldHaveSize 1
            paginatedAlbums.items[0].should {
                it.id shouldBe "1LoyJQVHPLHE3fCCS8Juek"
                it.name shouldBe "RAMMSTEIN"
                it.releaseDate shouldBe "2019-05-17"
                it.releaseDatePrecision shouldBe "day"

                it.images.shouldContainExactly(
                    Image("https://i.scdn.co/image/389c1df3f21fa93570dde0b75332e75ab91bd878", 300, 300)
                )
            }
        }

        results.artists.should { paginatedArtists ->
            paginatedArtists.total shouldBe 1
            paginatedArtists.items shouldHaveSize 1
            paginatedArtists.items[0].should {
                it.id shouldBe "6wWVKhxIU2cEi0K81v7HvP"
                it.name shouldBe "Rammstein"
                it.popularity shouldBe 87
                it.genres.shouldContainExactly(
                    "alternative metal",
                    "german metal",
                    "industrial",
                    "industrial metal",
                    "industrial rock",
                    "neue deutsche harte"
                )

                it.images.shouldContainExactly(
                    Image("https://i.scdn.co/image/d7bba2e8eb624d93d8cc7cb57d9ba5fb35f0f901", 320, 320)
                )
            }
        }

        results.tracks.should { paginatedTracks ->
            paginatedTracks.total shouldBe 1
            paginatedTracks.items shouldHaveSize 1

            paginatedTracks.items[0].should {
                it.id shouldBe "5vZ4IeUenK2cHub2d7yfWk"
                it.name shouldBe "RADIO"
                it.discNumber shouldBe 1
                it.trackNumber shouldBe 2
                it.duration shouldBe 277397
                it.explicit shouldBe false
            }
        }
    }

    @Test
    fun `When requesting too much resources at one time, then fail with IllegalArgumentException`() = runBlockingTest {
        val apiClient = spotifyClient { fail("Expected no network call.") }

        val artistIds = List(51) { "12Chz98pHFMPJEknJQMWvI" }
        shouldThrow<IllegalArgumentException> { apiClient.getSeveralArtists(artistIds) }

        val albumIds = List(21) { "5OZgDtx180ZZPMpm36J2zC" }
        shouldThrow<IllegalArgumentException> { apiClient.getSeveralAlbums(albumIds) }

        val trackIds = List(51) { "7f0vVL3xi4i78Rv5Ptn2s1" }
        shouldThrow<IllegalArgumentException> { apiClient.getSeveralTracks(trackIds) }

        val trackFeatureIds = List(101) { "7f0vVL3xi4i78Rv5Ptn2s1" }
        shouldThrow<IllegalArgumentException> { apiClient.getSeveralTrackFeatures(trackFeatureIds) }
    }

    @Test
    fun `When calling any endpoint, then send the provided UserAgent`() = runBlockingTest {
        val apiClient = spotifyClient { request ->
            request.headers[HttpHeaders.UserAgent] shouldBe TEST_USER_AGENT
            respondJson(SINGLE_TRACK)
        }

        apiClient.getTrack("7f0vVL3xi4i78Rv5Ptn2s1")
    }
}

/**
 * Asserts that this request is an HTTP GET Request on the Spotify Web API (api.spotify.com)
 * on the specified [endpoint][spotifyApiEndpoint].
 *
 * @param spotifyApiEndpoint The path that should be requested on the server.
 */
private infix fun HttpRequestData.shouldGetOnSpotifyEndpoint(spotifyApiEndpoint: String) {
    method shouldBe HttpMethod.Get
    url.host shouldBe "api.spotify.com"
    url.encodedPath shouldBe spotifyApiEndpoint
}

/**
 * Create an HTTP response with the provided [json] as the content body.
 *
 * @param json The content of the response, formatted as a valid JSON string.
 * @param status The status code of the HTTP response. Defaults to 200 (OK).
 */
private fun respondJson(@Language("JSON") json: String, status: HttpStatusCode = HttpStatusCode.OK) = respond(
    json, status, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
)

/**
 * Produces the JSON response body returned by the Spotify Web API when it returns a 400+ status code.
 *
 * @param status The status associated with the response.
 * @param message The error message provided as the `error` property of the JSON response.
 */
@Language("JSON")
private fun jsonApiError(status: HttpStatusCode, message: String): String = """{
    "error": {
      "status": ${status.value},
      "message": "$message"
    }
}""".trimIndent()

/**
 * Generate the response of the Spotify Web API in case of error.
 *
 * @param status The status code associated with the response. It should be a valid error code (>= 400).
 * @param message The message provided as the `error` property of the JSON response body.
 */
private fun respondJsonError(status: HttpStatusCode, message: String = status.description) =
    respondJson(
        jsonApiError(status, message), status
    )

private fun givenReachedRateLimit(retryAfter: Int): MockEngine {
    val engineConfig = MockEngineConfig()
    var firstRequest: HttpRequestData? = null

    engineConfig.addHandler { rateLimitedRequest ->
        firstRequest = rateLimitedRequest
        respond(
            jsonApiError(
                HttpStatusCode.TooManyRequests,
                "Rate limitation has been exceeded. Retry later."
            ),
            HttpStatusCode.TooManyRequests,
            headersOf(
                HttpHeaders.ContentType to listOf(ContentType.Application.Json.toString()),
                HttpHeaders.RetryAfter to listOf(retryAfter.toString())
            )
        )
    }

    engineConfig.addHandler { retriedRequest ->
        val originalRequest = firstRequest ?: fail("The request should have been issued before re-attempted.")
        // Check that the retried request is the same as the failed one.
        retriedRequest.method shouldBe originalRequest.method
        retriedRequest.url shouldBe originalRequest.url
        retriedRequest.headers shouldBe originalRequest.headers

        respondJson(SINGLE_ARTIST)
    }

    return MockEngine(engineConfig)
}
package com.github.thibseisel.sfyxplor

import com.github.thibseisel.api.spotify.AuthToken
import io.kotlintest.fail
import io.kotlintest.shouldThrow
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.*
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.Test

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
                ?: fail("Expected an Authorization header, but was missing.")

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
}

private fun respondJson(json: String, status: HttpStatusCode = HttpStatusCode.OK) = respond(
    json, status, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
)

private fun respondJsonError(status: HttpStatusCode, message: String = status.description) = respondJson(
    """{ 
        "status": ${status.value},
        "message": "$message"
    }""".trimIndent(),
    status
)
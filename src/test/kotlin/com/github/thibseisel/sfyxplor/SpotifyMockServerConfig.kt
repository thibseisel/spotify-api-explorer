package com.github.thibseisel.sfyxplor

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.*

const val VALID_TOKEN = "FreshToken"
const val EXPIRED_TOKEN = "ExpiredToken"

const val TEST_BASE64_KEY = "Y2xpZW50X2lkOmNsaWVudF9zZWNyZXQ="

val mockSpotifyApiConfig: HttpClientConfig<MockEngineConfig>.() -> Unit = {
    engine {
        addHandler {
            when (it.url.host) {
                "api.spotify.com" -> handleSpotifyApiRequest(it)
                "accounts.spotify.com" -> handleSpotifyAccountRequest(it)
                else -> respondError(HttpStatusCode.NotFound)
            }
        }
    }
}

private fun handleSpotifyApiRequest(request: HttpRequestData): HttpResponseData {
    if (HttpHeaders.Authorization !in request.headers) {
        return respondJsonError(HttpStatusCode.Unauthorized, "No token provided")
    } else {
        val token = request.headers[HttpHeaders.Authorization]
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substringAfter("Bearer ")
            ?.takeUnless(String::isEmpty)

        when (token) {
            null -> return respondJsonError(HttpStatusCode.Unauthorized)
            EXPIRED_TOKEN -> return respondJsonError(HttpStatusCode.Unauthorized, "The access token expired")
        }

        val path = request.url.encodedPath
        return when {
            path == "/v1/search" -> handleApiSearch(request.url.parameters)
            path == "/V1/artists" -> TODO()

            path.wildcardMatches("/v1/artists/*") -> {
                val artistId = path.split('/')[3]
                handleGetAnArtist(artistId)
            }

            path.wildcardMatches("/v1/artists/*/albums") -> {
                val artistId = path.split('/')[3]
                handleGetAnArtistAlbums(artistId)
            }

            path.wildcardMatches("/v1/albums/*") -> {
                val albumId = path.split('/')[3]
                handleGetAnAlbum(albumId)
            }

            path.wildcardMatches("/v1/audio-features/*") -> {
                val trackId = path.split(',')[3]
                handleGetAudioFeatures(trackId)
            }

            else -> respondJsonError(HttpStatusCode.NotFound, "Service not found")
        }
    }
}

private fun handleGetAnArtist(artistId: String): HttpResponseData = respondJson("{}")

private fun handleGetSeveralArtists(artistIds: List<String>) = respondJson("""{
    "artists": []
}""".trimIndent())

private fun handleGetAnAlbum(albumId: String) = respondJson("{}")

private fun handleGetAudioFeatures(trackId: String): HttpResponseData = respondJson("{}")

private fun String.wildcardMatches(pattern: String): Boolean {
    var scanIndex = 0

    for (subPattern in pattern.split('*')) {
        val indexOfPattern = indexOf(subPattern, scanIndex)
        if (indexOfPattern < 0) return false
        scanIndex = indexOfPattern + subPattern.length
    }

    return true
}

private fun handleApiSearch(queryParams: Parameters): HttpResponseData {
    val query = queryParams["q"] ?: return respondJsonError(HttpStatusCode.BadRequest, "No search query")
    val type = queryParams["type"] ?: return respondJsonError(HttpStatusCode.BadRequest, "Missing parameter type")
    val limit = queryParams["limit"]?.toInt()?.takeIf { it in 1..50 } ?: 20
    val offset = queryParams["offset"]?.toInt()?.takeIf { it in 0..10000 } ?: 0

    // TODO Return sample data based on the search.
    return respondJson("[]", HttpStatusCode.OK)
}

private fun handleGetAnArtistAlbums(artistId: String): HttpResponseData {
    // TODO Create sample data.
    return respondJson("[]", HttpStatusCode.OK)
}

private fun handleSpotifyAccountRequest(request: HttpRequestData): HttpResponseData {
    return if (request.method == HttpMethod.Post && request.url.encodedPath == "/api/token") {
        val clientKey = request.headers[HttpHeaders.Authorization]
            ?.substringAfter("Basic ", "")
            ?.takeUnless(String::isEmpty)

        if (clientKey == TEST_BASE64_KEY) {
            respondJson(
                """{
                    "access_token": "$VALID_TOKEN",
                    "token_type": "bearer",
                    "expired_in": 3600
                }""".trimIndent(), HttpStatusCode.OK
            )

        } else {
            respondError(HttpStatusCode.Unauthorized)
        }

    } else {
        respondJsonError(HttpStatusCode.NotFound, "Service not found")
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
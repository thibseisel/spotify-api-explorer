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

private fun handleSpotifyApiRequest(request: HttpRequestData): HttpResponseData = when {
    // Return 401 when not authorization
    HttpHeaders.Authorization !in request.headers ->
        respondJsonError(HttpStatusCode.Unauthorized, "No token provided")

    // Return 401 when used token expired
    request.headers[HttpHeaders.Authorization] == "Bearer $EXPIRED_TOKEN" ->
        respondJsonError(HttpStatusCode.Unauthorized, "The access token expired")

    else -> {
        val path = request.url.encodedPath
        when {
            path == "v1/search" -> handleApiSearch(request.url.parameters)
            path.wildcardMatches("v1/artists/*/albums") -> {
                val artistId = path.split('/')[2]
                handleGetAnArtistAlbums(artistId)
            }

            path.wildcardMatches("v1/albums/*") -> {
                val albumId = path.split('/')[2]
                handleGetAnAlbum(albumId)
            }

            path.wildcardMatches("v1/audio_features/*") -> {
                val trackId = path.split(',')[2]
                handleGetAudioFeatures(trackId)
            }

            else -> respondJsonError(HttpStatusCode.NotFound, "Service not found")
        }
    }
}

fun handleGetAudioFeatures(trackId: String): HttpResponseData {
     // TODO Create sample data
    return respondJson(HttpStatusCode.OK, "{}")
}

fun handleGetAnAlbum(albumId: String): HttpResponseData {
    // TODO Create sample data
    return respondJson(HttpStatusCode.OK, "{}")
}

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
    return respondJson(HttpStatusCode.OK, "[]")
}

private fun handleGetAnArtistAlbums(artistId: String): HttpResponseData {
    // TODO Create sample data.
    return respondJson(HttpStatusCode.OK,"[]")
}

private fun handleSpotifyAccountRequest(request: HttpRequestData): HttpResponseData {
    return if (request.method == HttpMethod.Post && request.url.encodedPath == "api/token") {
        respondJson(HttpStatusCode.OK, """{
            "access_token": "$VALID_TOKEN",
            "token_type": "bearer",
            "expired_in": 3600
        }""".trimIndent())
    } else {
        respondError(HttpStatusCode.Unauthorized)
    }
}

private fun respondJson(status: HttpStatusCode, json: String) = respond(
    json, status, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
)

private fun respondJsonError(status: HttpStatusCode, message: String) = respondJson(status, """{ 
    "status": ${status.value},
    "message": "$message"
}""".trimIndent())
package com.github.thibseisel.sfyxplor

import com.google.gson.annotations.SerializedName
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.Json
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import org.jetbrains.annotations.TestOnly

data class AuthToken(
    /** The access token string that should be used for authentication. */
    @SerializedName("access_token") val token: String,

    /**
     * The number of seconds since this token has been created after which this token becomes invalid.
     * An expired token cannot be used and should be renewed.
     */
    @SerializedName("expires_in") val expiresIn: Int
)

interface SpotifyApi {
    suspend fun searchArtists(query: String): List<SpotifyArtist>
    suspend fun findArtistAlbums(artistId: String): List<SpotifyAlbum>
    suspend fun findAlbumTracks(albumId: String) : List<SpotifyTrack>
    suspend fun getTrackFeatures(trackId: String): AudioFeatures
}

class SpotifyApiImpl
@TestOnly constructor(
    http: HttpClient,
    private val clientKey: String,
    private var accessToken: AuthToken?
) : SpotifyApi {

    private val http = http.config {
        Json {
            serializer = GsonSerializer()
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.spotify.com"
            }

            accept(ContentType.Application.Json)
            accessToken?.let { (token, _) ->
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }

    constructor(http: HttpClient, clientKey: String) : this(http, clientKey, null)

    override suspend fun searchArtists(query: String): List<SpotifyArtist> {
        if (accessToken == null) {
            accessToken = authenticate()
        }

        TODO("Search artist and configure deserialization of Paging objects")
    }

    override suspend fun findArtistAlbums(artistId: String): List<SpotifyAlbum> {
        if (accessToken == null) {
            accessToken = authenticate()
        }

        val albumPage = http.get<Paging<SpotifyAlbum>> {
            url { encodedPath = "v1/artists/$artistId/albums" }
        }

        TODO()
    }

    override suspend fun findAlbumTracks(albumId: String): List<SpotifyTrack> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTrackFeatures(trackId: String): AudioFeatures {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private suspend fun authenticate(): AuthToken = http.post<AuthToken> {
        url {
            protocol = URLProtocol.HTTPS
            host = "accounts.spotify.com"
            encodedPath = "api/token"
            parameter("grant_type", "client_credentials")
        }

        header(HttpHeaders.Authorization, "Basic $clientKey")
    }
}

class Paging<T>(
    val items: List<T>,
    val offset: Int,
    val limit: Int,
    val next: String?,
    val previous: String?
)
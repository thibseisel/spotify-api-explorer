package com.github.thibseisel.api.spotify

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
    suspend fun search(query: String, offset: Int = 0, limit: Int = 20): Paging<Artist>
    suspend fun findArtistAlbums(artistId: String, offset: Int = 0, limit: Int = 20): Paging<Album>
    suspend fun findAlbumTracks(albumId: String, offset: Int = 0, limit: Int = 20): Paging<Track>
    suspend fun getTrackFeatures(trackId: String): AudioFeatures?
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

    override suspend fun search(query: String, offset: Int, limit: Int): Paging<Artist> {
        if (accessToken == null) {
            accessToken = authenticate()
        }

        return http.get {
            url {
                encodedPath = "v1/search"
                parameter("q", query)
                parameter("type", "artist")
            }
        }
    }

    override suspend fun findArtistAlbums(
        artistId: String,
        offset: Int,
        limit: Int
    ): Paging<Album> {
        if (accessToken == null) {
            accessToken = authenticate()
        }

        val albumPage = http.get<Paging<Album>> {
            url { encodedPath = "v1/artists/$artistId/albums" }
        }

        TODO()
    }

    override suspend fun findAlbumTracks(
        albumId: String,
        offset: Int,
        limit: Int
    ): Paging<Track> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getTrackFeatures(trackId: String): AudioFeatures? {
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
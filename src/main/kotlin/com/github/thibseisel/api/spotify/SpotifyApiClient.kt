package com.github.thibseisel.api.spotify

import com.google.gson.annotations.*
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.io.*
import org.jetbrains.annotations.*
import java.util.*

enum class SearchType {
    ARTIST, ALBUM, TRACK
}

private suspend fun ByteReadChannel.readText(): String = buildString {
    while (!isClosedForRead) {
        readUTF8LineTo(this)
    }
}

/**
 * An object wrapping the values of a JSON array.
 *
 * When responding with a collection of values, most web services wrap them in a JSON object to prevent JSON Hijacking,
 * a known security vulnerability of JavaScript combined with a Cross-Site Request Forgery
 * allowing attackers to read sensitive data from JSON received in the browser.
 *
 * @param T Type of items contained in the array.
 * @param propertyName The name of the object property that maps to the [payload].
 * @param payload The content of the wrapped JSON array as a [List].
 */
internal class JsonWrapper<out T>(
    val propertyName: String,
    val payload: List<T>
)

class SearchResults(

    @SerializedName("artists")
    val artists: Paging<Artist>,

    @SerializedName("albums")
    val albums: Paging<Album>,

    @SerializedName("tracks")
    val tracks: Paging<Track>
)

/**
 * A character sequence generated by an authorization server that grants access to resources from the Spotify Web API.
 * An access token can be used a certain amount of time, then expires.
 * An expired token cannot be used and should be renewed by the authorization server.
 */
data class OAuthToken(

    /**
     * The access token string as issued by the authorization server.
     */
    @SerializedName("access_token")
    val token: String,

    /**
     * The type of token this is, typically just the string `bearer`.
     */
    @SerializedName("token_type")
    val tokenType: String,
    /**
     * The number of seconds the access token is granted for.
     * An expired token cannot be used and should be renewed.
     */
    @SerializedName("expires_in")
    val expiresIn: Int
)

/**
 * Thrown when authentication via the Spotify Authorization server failed
 * because the provided credentials were invalid.
 */
class AuthenticationException(statusCode: HttpStatusCode, message: String) : Exception("Error ${statusCode.value}: $message")

/**
 * Base class for errors that could occur when accessing resources from the Spotify Web API.
 */
sealed class SpotifyApiException : Exception()

/**
 * Thrown when access to an API resource failed because (re-)authentication is required.
 * This could be issued when:
 * - the client has not been authenticated, or
 * - the client's access token has expired.
 */
class AuthenticationRequired : SpotifyApiException()

/**
 * Thrown when a resource of the Spotify Web API cannot be accessed because it does not exists.
 * This may indicate that the id of the requested resource is invalid or no longer available.
 */
class ResourceNotFound : SpotifyApiException()

/**
 * Thrown when the Spotify Web API responded with an unexpected error.
 *
 * @property status The HTTP status code associated with the response.
 * @property message The message provided with the response describing the error.
 */
class UnexpectedCallFailure(val status: HttpStatusCode, override val message: String?) : SpotifyApiException()

/**
 * Provides an abstraction over the protocol used to access resources of the Spotify Web API.
 */
interface SpotifyApiClient {

    /**
     * Create an access token for the given [clientId] and keep it until it is expired.
     * Calls to other APIs of this class will use the generated token.
     *
     * @param clientId The public identifier of the client application.
     * This identifier is generated by Spotify on the developer dashboard.
     * @param clientSecret The secret key for the specified client.
     *
     * @return The token that has been generated.
     * @throws AuthenticationException If the provided [clientId] and/or [clientSecret] are invalid credentials.
     */
    suspend fun authenticate(clientId: String, clientSecret: String): OAuthToken

    /**
     * Get Spotify catalog information about artists, albums or tracks that match a keyword string.
     * Search results include hits from all the specified item [types][type].
     * For example, if [query] = `abacab` and [type] = `[album,track]`
     *
     * @param query Search keywords and optional fields filters and operators.
     * @param type The list of types to search across.
     * @param limit Maximum number of results to return. Default `20`, minimum `1` and maximum `50`.
     * __Note__: the limit is applied within each type, not on the total response.
     * For example, if the limit value is 3 and the type is `[artist,album]`, the result contains 3 artists and 3 albums.
     * @param offset The index of the first result to return. Defaults to `0` (the first result).
     * The maximum offset (including limit) is `10000`.
     */
    suspend fun search(
        query: String,
        type: Set<SearchType>,
        limit: Int = 20,
        offset: Int = 0
    ): SearchResults

    /**
     * Get Spotify catalog information for a single artist identified by its unique Spotify ID.
     *
     * @param id The Spotify ID for the artist.
     * @return The detailed information of this artist.
     *
     * @throws ResourceNotFound If the requested [id] does not match an existing artist.
     */
    suspend fun getArtist(id: String): Artist

    /**
     * Get Spotify catalog information for several artists based on their Spotify IDs.
     * If an artist is not found, a `null` is returned at the appropriate position.
     * Duplicates in [ids] results in duplicates in the returned artists.
     *
     * @param ids The Spotify IDs for the artists. Maximum `50` IDs.
     * @return The information for each artist, in the order requested.
     */
    suspend fun getSeveralArtists(ids: List<String>): List<Artist?>

    /**
     * Get Spotify catalog information about an artist’s albums.
     *
     * @param artistId The Spotify ID for the artist.
     * @param offset The index of the first album to return. Default: `0` (i.e., the first album).
     * Use with [limit] to get the next set of albums.
     * @param limit The number of albums to return. Default: `20`. Minimum: `1`. Maximum: `50`.
     *
     * @return A paginated list of albums where the requested artist participates.
     * @throws ResourceNotFound If the requested artist does not exist.
     */
    suspend fun getArtistAlbums(artistId: String, limit: Int = 20, offset: Int = 0): Paging<Album>

    /**
     * Get Spotify catalog information for a single album.
     *
     * @param id The Spotify ID for the album.
     *
     * @return The detailed information of this album.
     * @throws ResourceNotFound If the requested album does not exist.
     */
    suspend fun getAlbum(id: String): Album

    /**
     * Get Spotify catalog information for multiple albums identified by their Spotify IDs.
     * If an album is not found, a `null` is returned at the appropriate position.
     * Duplicates in [ids] results in duplicates in the returned albums.
     *
     * @param ids The Spotify IDs for the albums. Maximum: `20` IDs.
     * @return The information for each album, in the order requested.
     */
    suspend fun getSeveralAlbums(ids: List<String>): List<Album?>

    /**
     * Get Spotify catalog information about an album’s tracks.
     *
     * @param albumId The SpotifyID for the album.
     * @param offset The index of the first track to return. Default: `0`.
     * Use with limit to get the next set of tracks.
     * @param limit The maximum number of tracks to return. Default: `20`. Minimum: `1`. Maximum: `50`.
     *
     * @return A paginated list of tracks from the requested album.
     * @throws ResourceNotFound If the requested album does not exist.
     */
    suspend fun getAlbumTracks(albumId: String, limit: Int = 20, offset: Int = 0): Paging<Track>

    /**
     * Get Spotify catalog information for a single track identified by its unique Spotify ID.
     * @param id The Spotify ID for the track.
     *
     * @return The detailed information of this track.
     * @throws ResourceNotFound If the requested track does not exist.
     */
    suspend fun getTrack(id: String): Track

    /**
     * Get Spotify catalog information for multiple tracks identified by their Spotify IDs.
     * If a track is not found, a `null` is returned at the appropriate position.
     * Duplicates in [ids] results in duplicates in the returned tracks.
     *
     * @param ids The Spotify IDs for the tracks. Maximum: `50` IDs.
     * @return The information for each track, in the order requested.
     */
    suspend fun getSeveralTracks(ids: List<String>): List<Track?>

    /**
     * Get audio feature information for a single track identified by its unique Spotify ID.
     * @param trackId The Spotify ID for the track.
     *
     * @return The audio features for the requested track.
     * @throws ResourceNotFound If the requested track does not exist.
     */
    suspend fun getTrackFeatures(trackId: String): AudioFeatures

    /**
     * Get audio features for multiple tracks based on their Spotify IDs.
     * If a track is not found, a `null` is returned at the appropriate position.
     * Duplicates in [trackIds] results in duplicates in the returned tracks' features.
     *
     * @param trackIds The Spotify IDs for the tracks. Maximum: `100` IDs.
     * @return The audio features for each track, in the order requested.
     */
    suspend fun getSeveralTrackFeatures(trackIds: List<String>): List<AudioFeatures?>

    companion object {
        internal const val QUERY_Q = "q"
        internal const val QUERY_IDS = "ids"
        internal const val QUERY_LIMIT = "limit"
        internal const val QUERY_OFFSET = "offset"
        internal const val QUERY_TYPE = "type"
        internal const val QUERY_INCLUDE_GROUPS = "include_groups"

        /**
         * Create an instance of the default Spotify API client.
         *
         * @param engine The [HttpClientEngine] used to send requests.
         * @param userAgent The User-Agent string used to identify this client.
         */
        operator fun invoke(
            engine: HttpClientEngine,
            userAgent: String
        ): SpotifyApiClient = SpotifyApiClientImpl(engine, userAgent)
    }
}

/**
 * Default implementation of the [SpotifyApiClient] protocol.
 *
 * @constructor Create an instance of the Spotify client.
 * This constructor should only be used by testing code.
 *
 * @param engine The [HttpClientEngine] to use to send requests.
 * The engine implementation depends on the target platform.
 * @param userAgent The User-Agent string used to identify this client.
 * @param authToken A pre-provided access token, for testing purposes.
 */
internal class SpotifyApiClientImpl
@TestOnly constructor(
    engine: HttpClientEngine,
    userAgent: String,
    private var authToken: OAuthToken?
) : SpotifyApiClient {

    /**
     * Create an instance of the Spotify API client with a given [HttpClientEngine] and User-Agent.
     *
     * @param engine The engine used to send requests.
     * The engine implementation depends on the target platform.
     * @param userAgent The User-Agent string used to identify this client.
     */
    constructor(engine: HttpClientEngine, userAgent: String) : this(engine, userAgent, null)

    private val authService = HttpClient(engine) {

        install(UserAgent) {
            agent = userAgent
        }

        Json {
            serializer = GsonSerializer {
                disableHtmlEscaping()
                disableInnerClassSerialization()
            }
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "accounts.spotify.com"
                encodedPath = "api/token"
            }
        }

        HttpResponseValidator {
            validateResponse {
                if (it.status != HttpStatusCode.OK) {
                    throw AuthenticationException(it.status, it.content.readText())
                }
            }
        }
    }

    private val spotifyService = HttpClient(engine) {

        install(UserAgent) {
            agent = userAgent
        }

        Json {
            serializer = GsonSerializer {
                disableHtmlEscaping()
                disableInnerClassSerialization()

                registerTypeAdapterFactory(SearchableAdapter.Factory)
                registerTypeAdapterFactory(JsonWrapperTypeAdapter.Factory)
                registerTypeAdapter(MusicalMode::class.java, MusicalModeJsonAdapter())
                registerTypeAdapter(Pitch::class.java, SpotifyPitchJsonAdapter())
            }
        }

        install(RetryAfter)

        defaultRequest {
            accept(ContentType.Application.Json)
            url {
                protocol = URLProtocol.HTTPS
                host = "api.spotify.com"
            }

            authToken?.let { (token, tokenType, _) ->
                header(HttpHeaders.Authorization, "Bearer $token")
            }
        }

        HttpResponseValidator {
            throwOnHttpError()
        }
    }

    private fun HttpCallValidator.Config.throwOnHttpError() = validateResponse { response ->
        when (val status = response.status) {
            HttpStatusCode.Unauthorized -> throw AuthenticationRequired()
            HttpStatusCode.NotFound -> throw ResourceNotFound()

            else -> if (status.value > 400) {
                val responseContent = response.content.readText()
                throw UnexpectedCallFailure(response.status, responseContent)
            }
        }
    }

    override suspend fun authenticate(clientId: String, clientSecret: String): OAuthToken {
        val base64Key = Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray())

        return authService.post<OAuthToken> {
            header(HttpHeaders.Authorization, "Basic $base64Key")
            body = FormDataContent(Parameters.build {
                append("grant_type", "client_credentials")
            })
        }.also { authToken = it }
    }

    override suspend fun search(
        query: String,
        type: Set<SearchType>,
        limit: Int,
        offset: Int
    ): SearchResults = spotifyService.get("/v1/search") {
        require(limit in 1..50)
        require(offset in 0..10000)

        url {
            parameter(SpotifyApiClient.QUERY_OFFSET, offset.toString())
            parameter(SpotifyApiClient.QUERY_LIMIT, limit.toString())
            parameter(SpotifyApiClient.QUERY_Q, query)

            if (type.isNotEmpty()) {
                parameters.appendAll(SpotifyApiClient.QUERY_TYPE, type.map { it.name.toLowerCase() })
            }
        }

    }

    override suspend fun getArtist(id: String): Artist = spotifyService.get("/v1/artists/$id")

    override suspend fun getSeveralArtists(ids: List<String>): List<Artist?> =
        spotifyService.get<JsonWrapper<Artist?>>("/v1/artists") {
            require(ids.size in 0..50)
            url { parameters.appendAll(SpotifyApiClient.QUERY_IDS, ids) }
        }.payload

    override suspend fun getArtistAlbums(
        artistId: String,
        limit: Int,
        offset: Int
    ): Paging<Album> = spotifyService.get("/v1/artists/$artistId/albums") {
        require(offset >= 0)
        require(limit in 1..20)

        parameter(SpotifyApiClient.QUERY_OFFSET, offset.toString())
        parameter(SpotifyApiClient.QUERY_LIMIT, limit.toString())
        url { parameters.appendAll(SpotifyApiClient.QUERY_INCLUDE_GROUPS, listOf("album", "single")) }
    }

    override suspend fun getAlbum(id: String): Album = spotifyService.get("/v1/albums/$id")

    override suspend fun getSeveralAlbums(ids: List<String>): List<Album?> =
        spotifyService.get<JsonWrapper<Album?>>("/v1/albums") {
            require(ids.size in 0..20)
            url { parameters.appendAll(SpotifyApiClient.QUERY_IDS, ids) }
        }.payload

    override suspend fun getAlbumTracks(
        albumId: String,
        limit: Int,
        offset: Int
    ): Paging<Track> = spotifyService.get("/v1/albums/$albumId/tracks") {
        require(offset >= 0)
        require(limit in 1..50)

        parameter(SpotifyApiClient.QUERY_OFFSET, offset.toString())
        parameter(SpotifyApiClient.QUERY_LIMIT, limit.toString())
    }

    override suspend fun getTrack(id: String): Track = spotifyService.get("/v1/tracks/$id")

    override suspend fun getSeveralTracks(ids: List<String>): List<Track?> =
        spotifyService.get<JsonWrapper<Track?>>("/v1/tracks") {
            require(ids.size in 0..50)
            url { parameters.appendAll("ids", ids) }
        }.payload

    override suspend fun getTrackFeatures(trackId: String): AudioFeatures =
        spotifyService.get("/v1/audio-features/$trackId")

    override suspend fun getSeveralTrackFeatures(trackIds: List<String>): List<AudioFeatures?> =
        spotifyService.get<JsonWrapper<AudioFeatures?>>("/v1/audio-features") {
            require(trackIds.size in 0..100)
            url { parameters.appendAll("ids", trackIds) }
        }.payload
}
package com.github.thibseisel.sfyxplor

import com.github.thibseisel.api.spotify.*
import java.util.*

class SpotifySource(
    private val clientId: String,
    private val clientSecret: String,
    private val apiClient: SpotifyApiClient
) {
    private var token: OAuthToken? = null

    suspend fun search(query: String, type: String?): SearchResults {
        val searchType = enumValues<SearchType>()
            .firstOrNull { it.name.equals(type, true) }
            ?.let { setOf(it) }
            ?: EnumSet.allOf(SearchType::class.java)

        authenticateIfRequired()
        return apiClient.search(query, searchType)
    }

    suspend fun getArtistAlbums(artistId: String): Paging<Album> {
        authenticateIfRequired()
        return apiClient.getArtistAlbums(artistId)
    }

    suspend fun getAlbumTracks(albumId: String): Paging<Track> {
        authenticateIfRequired()
        return apiClient.getAlbumTracks(albumId)
    }

    suspend fun getTrackFeatures(trackId: String): AudioFeatures {
        authenticateIfRequired()
        return apiClient.getTrackFeatures(trackId)
    }

    private suspend fun authenticateIfRequired() {
        if (token == null) {
            token = apiClient.authenticate(clientId, clientSecret)
        }
    }
}
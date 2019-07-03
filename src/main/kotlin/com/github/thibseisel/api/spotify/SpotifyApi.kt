package com.github.thibseisel.api.spotify

@Deprecated(
    "The API interface has changed.",
    ReplaceWith("SpotifyApiClient", "com.github.thibseisel.sfyxplor")
)
interface SpotifyApi {
    suspend fun search(query: String, offset: Int = 0, limit: Int = 20): Paging<Artist>
    suspend fun findArtistAlbums(artistId: String, offset: Int = 0, limit: Int = 20): Paging<Album>
    suspend fun findAlbumTracks(albumId: String, offset: Int = 0, limit: Int = 20): Paging<Track>
    suspend fun getTrackFeatures(trackId: String): AudioFeatures?
}
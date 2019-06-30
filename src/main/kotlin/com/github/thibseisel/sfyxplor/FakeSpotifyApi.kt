package com.github.thibseisel.sfyxplor

import com.github.thibseisel.api.spotify.*

class FakeSpotifyApi : SpotifyApi {

    override suspend fun search(query: String, offset: Int, limit: Int): Paging<Artist> = Paging(
        listOf(
            Artist("abcde", "Muse", 82, listOf("modern rock", "alternative"), emptyList()),
            Artist("defgh", "Foo Fighters", 82, listOf("alternative metal", "rock"), emptyList())
        ),
        offset,
        limit,
        2
    )

    override suspend fun findArtistAlbums(
        artistId: String,
        offset: Int,
        limit: Int
    ): Paging<Album> = when (artistId) {
        "abcde" -> Paging(
            listOf(
                Album("azerty", "The 2nd Law", "2011", "year", emptyList()),
                Album("poiuyt", "Simulation Theory", "2018", "year", emptyList())
            ),
            offset,
            limit,
            2
        )

        "defgh" -> Paging(
            listOf(Album("djazao", "Concrete and Gold", "2017", "year", emptyList())),
            offset,
            limit,
            2
        )

        else -> Paging(emptyList(), 0, 0, 0)
    }

    override suspend fun findAlbumTracks(
        albumId: String,
        offset: Int,
        limit: Int
    ): Paging<Track> = when(albumId) {
        "azerty" -> Paging(
            listOf(Track("ckzzco", "Big Freeze", 1, 9, 279000, false)),
            offset,
            limit,
            total = 1
        )

        "poiuyt" -> Paging(
            listOf(
                Track("wlpzap", "Pressure", 1, 3, 235000, false),
                Track(",xncjdi", "Something Human", 1, 7, 224000, false)
            ),
            offset,
            limit,
            total = 2
        )

        "djozao" -> Paging(
            listOf(
                Track("ozahde", "Dirty Water", 1, 6, 320000, false)
            ),
            offset,
            limit,
            total = 1
        )

        else -> Paging(emptyList(), 0, 0, 0)
    }

    override suspend fun getTrackFeatures(trackId: String): AudioFeatures? = when(trackId) {
        "ckzzco" -> AudioFeatures("ckzzco", null, MusicalMode.MAJOR, 75f, 1, 0.5f, 0.2f, 0.4f, 0.6f, 0.1f, 0.2f, 0.1f, 0.5f)
        "wlpzap" -> AudioFeatures("wlpzap", null, MusicalMode.MINOR, 100f, 1, 0.7f, 0.4f, 0.6f, 0.8f, 0.2f, 0.3f, 0.1f, 0.4f)
        "ozahde" -> AudioFeatures("ozahde", null, MusicalMode.MAJOR, 80f, 2, 0.5f, 0.5f, 0.3f, 0.7f, 0.2f, 0.3f, 0.1f, 0.5f)
        else -> null
    }
}
package com.github.thibseisel.sfyxplor

class SpotifyAlbum(

    /**
     * The unique identifier of this album on Spotify servers.
     */
    val id: String,

    /**
     * The name of the album.
     */
    val name: String,

    /**
     * The date the album was first released, for example `1981`.
     * Depending on the precision, it might be shown as `1981-12` or `1981-12-15`.
     */
    val releaseDate: String,

    /**
     * The precision with which release_date value is known: year , month , or day.
     */
    val releaseDatePrecision: String,

    /**
     * The cover art for the album in various sizes, widest first.
     */
    val images: List<SpotifyImage>
)
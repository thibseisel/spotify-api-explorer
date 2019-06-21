package com.github.thibseisel.sfyxplor

import com.google.gson.annotations.SerializedName

class SpotifyAlbum(

    /**
     * The unique identifier of this album on Spotify servers.
     */
    @SerializedName("id")
    val id: String,

    /**
     * The name of the album.
     */
    @SerializedName("name")
    val name: String,

    /**
     * The date the album was first released, for example `1981`.
     * Depending on the precision, it might be shown as `1981-12` or `1981-12-15`.
     */
    @SerializedName("release_date")
    val releaseDate: String,

    /**
     * The precision with which release_date value is known: year , month , or day.
     */
    @SerializedName("release_date_precision")
    val releaseDatePrecision: String,

    /**
     * The cover art for the album in various sizes, widest first.
     */
    @SerializedName("images")
    val images: List<SpotifyImage>
)
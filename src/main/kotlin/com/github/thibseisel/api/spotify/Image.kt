package com.github.thibseisel.api.spotify

import com.google.gson.annotations.SerializedName

/**
 * An image illustrating a media hosted on Spotify.
 */
data class Image(

    /**
     * The source URL of the image.
     */
    @SerializedName("url")
    val url: String,

    /**
     * The image width in pixels.
     * `null` if unknown.
     */
    @SerializedName("width")
    val width: Int?,

    /**
     * The image height in pixels.
     * `null` if unknown.
     */
    @SerializedName("height")
    val height: Int?
)
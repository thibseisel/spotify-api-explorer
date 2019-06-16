package com.github.thibseisel.sfyxplor

/**
 * An image illustrating a media hosted on Spotify.
 */
class SpotifyImage(

    /**
     * The source URL of the image.
     */
    val url: String,

    /**
     * The image width in pixels.
     * `null` if unknown.
     */
    val width: Int?,

    /**
     * The image height in pixels.
     * `null` if unknown.
     */
    val height: Int?
)
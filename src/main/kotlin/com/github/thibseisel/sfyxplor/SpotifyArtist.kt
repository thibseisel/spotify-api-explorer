package com.github.thibseisel.sfyxplor

class SpotifyArtist(

    /**
     * The unique identifier of this artist on Spotify servers.
     */
    val id: String,

    /**
     * The name of this artist.
     */
    val name: String,

    /**
     * The popularity of the artist.
     * The value will be between `0` and `100`, with `100` being the most popular.
     * The artist’s popularity is calculated from the popularity of all the artist’s tracks.
     */
    val popularity: Int,

    /**
     * A list of the genres the artist is associated with.
     * For example: "Prog Rock" , "Post-Grunge".
     * (If not yet classified, the array is empty.)
     */
    val genres: List<String>,

    /**
     * Images of the artist in various sizes, widest first.
     */
    val images: List<SpotifyImage>
)


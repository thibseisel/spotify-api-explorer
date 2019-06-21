package com.github.thibseisel.sfyxplor

import com.google.gson.annotations.SerializedName

class SpotifyArtist(

    /**
     * The unique identifier of this artist on Spotify servers.
     */
    @SerializedName("id")
    val id: String,

    /**
     * The name of this artist.
     */
    @SerializedName("name")
    val name: String,

    /**
     * The popularity of the artist.
     * The value will be between `0` and `100`, with `100` being the most popular.
     * The artist’s popularity is calculated from the popularity of all the artist’s tracks.
     */
    @SerializedName("popularity")
    val popularity: Int,

    /**
     * A list of the genres the artist is associated with.
     * For example: "Prog Rock" , "Post-Grunge".
     * (If not yet classified, the array is empty.)
     */
    @SerializedName("genres")
    val genres: List<String>,

    /**
     * Images of the artist in various sizes, widest first.
     */
    @SerializedName("images")
    val images: List<SpotifyImage>
)


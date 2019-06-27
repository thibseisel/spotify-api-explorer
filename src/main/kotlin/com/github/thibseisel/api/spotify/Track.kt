package com.github.thibseisel.api.spotify

import com.google.gson.annotations.SerializedName

class Track(

    /**
     * The unique identifier of this track on Spotify servers.
     */
    @SerializedName("id")
    override val id: String,

    /**
     * The name of the track.
     */
    @SerializedName("name")
    val name: String,

    /**
     * The disc number (usually 1 unless the album consists of more than one disc).
     */
    @SerializedName("disc_number")
    val discNumber: Int,

    /**
     * The number of the track.
     * If an album has several discs, the track number is the number on the specified disc.
     */
    @SerializedName("track_number")
    val trackNumber: Int,

    /**
     * The track length in milliseconds.
     */
    @SerializedName("duration_ms")
    val duration: Int,

    /**
     * Whether or not the track has explicit lyrics ( true = yes it does; false = no it does not OR unknown).
     */
    @SerializedName("explicit")
    val explicit: Boolean

) : SearchableResource

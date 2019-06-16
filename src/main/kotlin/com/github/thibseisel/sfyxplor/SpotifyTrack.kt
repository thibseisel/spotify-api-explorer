package com.github.thibseisel.sfyxplor

class SpotifyTrack(

    /**
     * The unique identifier of this track on Spotify servers.
     */
    val id: String,

    /**
     * The name of the track.
     */
    val name: String,

    /**
     * The disc number (usually 1 unless the album consists of more than one disc).
     */
    val discNumber: Int,

    /**
     * The number of the track.
     * If an album has several discs, the track number is the number on the specified disc.
     */
    val trackNumber: Int,

    /**
     * The track length in milliseconds.
     */
    val duration: Int,

    /**
     * Whether or not the track has explicit lyrics ( true = yes it does; false = no it does not OR unknown).
     */
    val explicit: Boolean
)

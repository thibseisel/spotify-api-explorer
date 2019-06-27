package com.github.thibseisel.api.spotify

import com.google.gson.annotations.SerializedName

class Paging<T>(

    /**
     * The requested data.
     */
    @SerializedName("items")
    val items: List<T>,

    /**
     * The offset of the items returned (as set in the query or by default).
     */
    @SerializedName("offset")
    val offset: Int,

    /**
     * The maximum number of items in the response (as set in the query or by default).
     */
    @SerializedName("limit")
    val limit: Int,

    /**
     * The maximum number of items available to return.
     */
    @SerializedName("total")
    val total: Int
)
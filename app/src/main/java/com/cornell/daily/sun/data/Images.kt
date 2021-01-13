package com.cornell.daily.sun.data

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName(value = "medium_large")
    val mediumLarge: ImageInfo,
    val thumbnail: ImageInfo,
    val full: ImageInfo
)

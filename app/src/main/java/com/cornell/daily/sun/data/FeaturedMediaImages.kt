package com.cornell.daily.sun.data

class FeaturedMediaImages internal constructor(
    var mediumLarge: ImageInfo,
    var thumbnail: ImageInfo,
    var full: ImageInfo
) {
    inner class ImageInfo internal constructor(var url: String, var width: Int, var height: Int)
}
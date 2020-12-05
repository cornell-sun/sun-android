package com.cornell.daily.sun.data

class SuggestedStoryObject internal constructor(
    var postID: Int,
    var title: String,
    var authors: Array<AuthorObject>,
    featuredMediaImages: FeaturedMediaImages
) {
    var featuredMediaImages: FeaturedMediaImages

    init {
        this.featuredMediaImages = featuredMediaImages
    }
}
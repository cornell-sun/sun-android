package com.cornell.daily.sun.data

class SuggestedStoryObject internal constructor(
    var postID: Int,
    var title: String,
    var authors: Array<Author>,
    featuredMediaImages: Images
) {
    var featuredMediaImages: Images = featuredMediaImages

}
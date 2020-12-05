package com.cornell.daily.sun.data

import java.util.*

data class PostObject(
    val storeDate: Date?,
    val didSave: Boolean?,
    val id: Int,
    val title: String?,
    val content: String?,
    val excerpt: String?,
    val link: String?,
    val authors: List<AuthorObject>?,
    val featuredMediaImages: FeaturedMediaImages?,
    val featuredMediaCaption: String?,
    val featuredMediaCredit: String?,
    val categories: List<String>?,
    val primaryCategory: String?,
    val tags: List<String>,
    val postAttachments: List<PostAttachmentObject>,
    val postType: PostType?,
    val suggestedStories: List<SuggestedStoryObject>
)

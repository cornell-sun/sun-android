package com.cornell.daily.sun.data

sealed class PostContent
data class PostMeta(
    val section: String?,
    val title: String?,
    val image: String?,
    val imageCaption: String?,
    val imageCredit: String?,
    val author: String?,
    val date: String?
) : PostContent()
data class Paragraph(val text: String) : PostContent()
data class Image(val url: String) : PostContent()
data class ImageCredit(val text: String) : PostContent()
data class Blockquote(val text: String) : PostContent()
data class Caption(val text: String) : PostContent()
data class Heading(val text: String) : PostContent()
data class SuggestedPosts(val posts: List<PostInfoDict>) : PostContent()

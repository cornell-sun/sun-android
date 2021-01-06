package com.cornell.daily.sun.data

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Data class representing a Daily Sun news article
 */
data class Post(
    val storeDate: Date?,
    val didSave: Boolean?,
    val id: Int,
    val title: String?,
    val content: String?,
    val excerpt: String?,
    val link: String?,
    @SerializedName(value = "author_dict")
    val authors: List<Author>?,
    @SerializedName(value = "featured_media_url_string")
    val featuredMediaImages: Images?,
    val featuredMediaCaption: String?,
    val featuredMediaCredit: String?,
    val categories: List<String>?,
    val primaryCategory: String?,
    val tags: List<String>,
    val postAttachments: List<PostAttachmentObject>,
    val postType: PostType?
) {
    /**
     * @return Author names separated by "and"
     */
    fun getByline(): String? {
        return authors?.foldIndexed("") { i, acc, author ->
            acc + author.name + (if (i < authors.size - 1) {
                " and "
            } else {
                ""
            })
        }
    }

    /**
     * @return Url of the medium sized article image
     */
    fun getMediumImageUrl(): String? {
        return featuredMediaImages?.mediumLarge?.url
    }
}

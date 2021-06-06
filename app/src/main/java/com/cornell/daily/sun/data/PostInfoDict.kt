package com.cornell.daily.sun.data

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.ParsePosition
import java.util.*

/**
 * Data class representing a Daily Sun news article
 */
data class PostInfoDict(
    val date: String?,
    val didSave: Boolean?,
    @SerializedName(value = "id", alternate = ["post_id"])
    val id: Int,
    val title: String?,
    @SerializedName(value = "post_content_no_srcset")
    val content: String?,
    val excerpt: String?,
    val link: String?,
    @SerializedName(value = "author_dict")
    val authors: List<Author>?,
    @SerializedName(value = "featured_media_url_string", alternate = ["featured_media"])
    val featuredMediaImages: Images?,
    @SerializedName(value = "featured_media_caption")
    val featuredMediaCaption: String?,
    @SerializedName(value = "featured_media_credit")
    val featuredMediaCredit: String?,
    val categories: List<String>?,
    @SerializedName(value = "primary_category")
    val primaryCategory: String?,
    val tags: List<String>,
    @SerializedName(value = "suggested_article_ids")
    val suggestedPosts: List<PostInfoDict>,
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

    fun getFormattedDate(): String {
        val fixedISODate = "$date-05:00"
        val date = ISO8601Utils.parse(fixedISODate, ParsePosition(0))
        return String.format("%1\$tB %1\$td, %1\$tY", date)
    }

    fun getDaysElapsedFromPost(): String {
        val fixedISODate = "$date-05:00"
        val date = ISO8601Utils.parse(fixedISODate, ParsePosition(0))
        val today = Calendar.getInstance().time
        val diff = today.time - date.time
        val numOfDays = (diff / (1000 * 60 * 60 * 24))
        return numOfDays.toString()
    }
}

package com.cornell.daily.sun.adapters

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.*
import com.squareup.picasso.Picasso
import kotlin.reflect.KSuspendFunction1

class PostContentAdapter(
    private val context: Context?,
    private val loadPost: KSuspendFunction1<Int, Unit>
) :
    ListAdapter<PostContent, RecyclerView.ViewHolder>(PostContentDiffCallback()) {

    class ParagraphHolder internal constructor(paragraphView: View) :
        RecyclerView.ViewHolder(paragraphView) {
        val paragraphText: TextView = paragraphView.findViewById(R.id.post_paragraph_text)
    }

    class HeaderHolder internal constructor(headerView: View) :
        RecyclerView.ViewHolder(headerView) {
        val postSectionName: TextView = headerView.findViewById(R.id.post_view_section)
        val postTitle: TextView = headerView.findViewById(R.id.post_view_title)
        val postImage: ImageView = headerView.findViewById(R.id.post_view_image)
        val postAuthor: TextView = headerView.findViewById(R.id.post_view_author)
        val postImageCaption: TextView =
            headerView.findViewById(R.id.post_view_image_caption)
        val postImageCredit: TextView =
            headerView.findViewById(R.id.post_view_image_credit)
        val postViewDate: TextView = headerView.findViewById(R.id.post_view_date)
    }

    class SuggestedPostsHolder internal constructor(suggestedPostsView: View) :
        RecyclerView.ViewHolder(suggestedPostsView) {
        val suggestedPostsRecyclerView: RecyclerView =
            suggestedPostsView.findViewById(R.id.suggested_posts_list)
    }

    class InlineImageHolder internal constructor(inlineImageView: View) :
        RecyclerView.ViewHolder(inlineImageView) {
        val postInlineImage: ImageView = inlineImageView.findViewById(R.id.post_view_inline_image)
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is PostMeta -> 0
            is Paragraph -> 1
            is Image -> 2
            is SuggestedPosts -> 3
            is Caption -> 4
            is Heading -> 5
            is Blockquote -> 6
            is ImageCredit -> 7
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> HeaderHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_post_header, parent, false) as View
            )
            1 -> ParagraphHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_post_paragraph, parent, false) as View
            )
            2 -> InlineImageHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_post_inline_image, parent, false) as View
            )
            3 -> SuggestedPostsHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_post_suggested_section, parent, false) as View
            )
            4 -> ParagraphHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_post_paragraph, parent, false) as View
            )
            5 -> ParagraphHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_post_paragraph, parent, false) as View
            )
            6 -> ParagraphHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_post_paragraph, parent, false) as View
            )
            7 -> ParagraphHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_post_paragraph, parent, false) as View
            )
            else -> throw Exception("Invalid View Type in PostContentAdapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val currentPostContent = currentList[position]) {
            is PostMeta -> {
                val headerHolder = holder as HeaderHolder
                headerHolder.postAuthor.text = currentPostContent.author
                Picasso.get().load(currentPostContent.image).fit().centerCrop()
                    .into(headerHolder.postImage)
                headerHolder.postImageCaption.text = currentPostContent.imageCaption
                headerHolder.postAuthor.text = currentPostContent.author
                headerHolder.postImageCredit.text = currentPostContent.imageCredit
                headerHolder.postViewDate.text = currentPostContent.date
                headerHolder.postSectionName.text = currentPostContent.section
                headerHolder.postTitle.text = currentPostContent.title
            }
            is Paragraph -> {
                val paragraphHolder = holder as ParagraphHolder
                paragraphHolder.paragraphText.movementMethod = LinkMovementMethod.getInstance()
                if (Build.VERSION.SDK_INT >= 24) {
                    paragraphHolder.paragraphText.text =
                        Html.fromHtml(currentPostContent.text, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    paragraphHolder.paragraphText.text = Html.fromHtml(currentPostContent.text)
                }
            }
            is SuggestedPosts -> {
                val suggestedPostsHolder = holder as SuggestedPostsHolder
                val suggestedSectionAdapter = PostSuggestedSectionAdapter(context, loadPost)
                suggestedSectionAdapter.submitList(currentPostContent.posts)
                suggestedPostsHolder.suggestedPostsRecyclerView.apply {
                    adapter = suggestedSectionAdapter
                    layoutManager = LinearLayoutManager(context)
                }
                suggestedPostsHolder.suggestedPostsRecyclerView.setHasFixedSize(true)
            }
            is Image -> {
                val inlineImageHolder = holder as InlineImageHolder
                Picasso.get().load(currentPostContent.url).fit().centerInside()
                    .into(inlineImageHolder.postInlineImage)
            }
            else -> {
            }
        }
    }
}

private class PostContentDiffCallback : DiffUtil.ItemCallback<PostContent>() {
    override fun areItemsTheSame(oldItem: PostContent, newItem: PostContent): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: PostContent, newItem: PostContent): Boolean {
        return when (oldItem) {
            is Paragraph ->
                when (newItem) {
                    is Paragraph -> oldItem == newItem
                    else -> false
                }
            is PostMeta ->
                when (newItem) {
                    is PostMeta -> oldItem == newItem
                    else -> false
                }
            is Image ->
                when (newItem) {
                    is Image -> oldItem == newItem
                    else -> false
                }
            is ImageCredit -> when (newItem) {
                is ImageCredit -> oldItem == newItem
                else -> false
            }
            is Blockquote -> when (newItem) {
                is Blockquote -> oldItem == newItem
                else -> false
            }
            is Caption -> when (newItem) {
                is Caption -> oldItem == newItem
                else -> false
            }
            is SuggestedPosts -> when (newItem) {
                is SuggestedPosts -> oldItem == newItem
                else -> false
            }
            is Heading -> when (newItem) {
                is Heading -> oldItem == newItem
                else -> false
            }
        }
    }
}
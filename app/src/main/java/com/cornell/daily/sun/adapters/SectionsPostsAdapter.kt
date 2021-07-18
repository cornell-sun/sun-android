package com.cornell.daily.sun.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.PostInfoDict
import com.squareup.picasso.Picasso

/**
 * Recycler View Adapter for posts within a section or within For You page
 */
class SectionsPostsAdapter(
    private val selectPostFn: (PostInfoDict) -> Unit,
    private val context: Context?
) : PagingDataAdapter<PostInfoDict, SectionsPostsAdapter.FeedPostHolder>(
    PostInfoDictDiffCallback()
) {
    class FeedPostHolder internal constructor(feedPostView: View) :
        RecyclerView.ViewHolder(feedPostView) {
        val feedPostTimeStamp: TextView = feedPostView.findViewById(R.id.feed_post_time_stamp)
        val feedPostTitle: TextView = feedPostView.findViewById(R.id.feed_post_title)
        val feedPostAuthor: TextView = feedPostView.findViewById(R.id.feed_post_author)
        val feedPostImage: ImageView = feedPostView.findViewById(R.id.feed_post_image)
    }

    override fun onBindViewHolder(holder: FeedPostHolder, position: Int) {
        val currentPost = this.getItem(position)
        holder.feedPostTitle.text = currentPost?.title
        holder.feedPostAuthor.text = context?.getString(R.string.byline, currentPost?.getByline())
        val daysElapsedSincePost = currentPost?.getDaysElapsedFromPost()
        val timeStampText = when (daysElapsedSincePost?.toInt()) {
            0 -> "Today"
            1 -> context?.getString(R.string.article_one_day_ago, daysElapsedSincePost)
            else -> context?.getString(
                R.string.article_days_ago,
                currentPost?.getDaysElapsedFromPost()
            )
        }
        holder.feedPostTimeStamp.text = timeStampText
        if (currentPost != null) {
            Picasso.get().load(currentPost.getMediumImageUrl()).fit().centerCrop()
                .into(holder.feedPostImage)
        }
        holder.itemView.setOnClickListener {
            if (currentPost != null) {
                selectPostFn(currentPost)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedPostHolder {
        val feedPostView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_feed_post, parent, false)
        return FeedPostHolder(feedPostView)
    }
}
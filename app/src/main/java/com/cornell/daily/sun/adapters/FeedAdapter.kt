package com.cornell.daily.sun.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.PostInfoDict

class FeedAdapter(private val selectPostFn: (PostInfoDict) -> Unit)
: PagingDataAdapter<PostInfoDict, FeedAdapter.FeedPostHolder>(
    PostInfoDictDiffCallback()
) {
    class FeedPostHolder internal constructor(feedPostView: View): RecyclerView.ViewHolder(feedPostView) {
        val feedPostTitle: TextView = itemView.findViewById(R.id.feed_post_title)
    }

    override fun onBindViewHolder(holder: FeedPostHolder, position: Int) {
        val currentPost = this.getItem(position)
        holder.feedPostTitle.text = currentPost?.title
        holder.itemView.setOnClickListener {
            if (currentPost != null) {
                selectPostFn(currentPost)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedPostHolder {
        val feedPostView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_feed_post, parent, false)
        return FeedPostHolder(feedPostView)
    }
}
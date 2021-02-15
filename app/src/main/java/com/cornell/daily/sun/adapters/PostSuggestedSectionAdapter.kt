package com.cornell.daily.sun.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.PostInfoDict
import com.squareup.picasso.Picasso

class PostSuggestedSectionAdapter(private val context: Context?) :
    ListAdapter<PostInfoDict, PostSuggestedSectionAdapter.SuggestedPostHolder>(
        PostInfoDictDiffCallback()
    ) {

    class SuggestedPostHolder internal constructor(suggestedPostView: View) :
        RecyclerView.ViewHolder(suggestedPostView) {
        val suggestedPostTitle: TextView = suggestedPostView.findViewById(R.id.suggested_post_title)
        val suggestedPostAuthor: TextView =
            suggestedPostView.findViewById(R.id.suggested_post_author)
        val suggestedPostImage: ImageView =
            suggestedPostView.findViewById(R.id.suggested_post_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedPostHolder {
        return SuggestedPostHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_post_suggested_section_cell, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: SuggestedPostHolder, position: Int) {
        val currentSuggestedPost = currentList[position]
        holder.suggestedPostTitle.text = currentSuggestedPost.title
        holder.suggestedPostAuthor.text =
            context?.getString(R.string.byline, currentSuggestedPost.getByline())
        Picasso.get().load(currentSuggestedPost.getMediumImageUrl()).fit().centerCrop()
            .into(holder.suggestedPostImage)

    }
}
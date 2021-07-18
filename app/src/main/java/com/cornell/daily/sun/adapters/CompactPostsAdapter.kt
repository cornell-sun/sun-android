package com.cornell.daily.sun.adapters

import android.content.Context
import android.text.Html
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
 * Recycler View Adapter meant for compact post views used
 * in Search, etc
 */
class CompactPostsAdapter(
    private val context: Context?,
    private val selectPostFn: (PostInfoDict) -> Unit
) :
    PagingDataAdapter<PostInfoDict, CompactPostsAdapter.CompactPostHolder>(PostInfoDictDiffCallback()) {
    class CompactPostHolder internal constructor(compactCellView: View) :
        RecyclerView.ViewHolder(compactCellView) {
        val compactCellTitle: TextView =
            compactCellView.findViewById(R.id.compact_article_cell_title)
        val compactCellImage: ImageView =
            compactCellView.findViewById(R.id.compact_article_cell_image)
        val compactCellAuthor: TextView =
            compactCellView.findViewById(R.id.compact_article_cell_author)
        val compactCellSection: TextView =
            compactCellView.findViewById(R.id.compact_article_cell_section)
    }

    override fun onBindViewHolder(holder: CompactPostHolder, position: Int) {
        val currentPost = this.getItem(position)
        holder.compactCellTitle.text = currentPost?.title
        holder.compactCellSection.text = Html.fromHtml(currentPost?.primaryCategory)
        holder.compactCellAuthor.text = context?.getString(R.string.byline, currentPost?.getByline())
        Picasso.get().load(currentPost?.getMediumImageUrl()).fit().centerCrop()
            .into(holder.compactCellImage)
        holder.itemView.setOnClickListener {
            selectPostFn(currentPost!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompactPostHolder {
        return CompactPostHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_compact_article, parent, false)
        )
    }
}
package com.cornell.daily.sun.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.PostInfoDict

class CompactPostsAdapter(
    private val context: Context?,
    private val selectionPostFn: (PostInfoDict) -> Unit
) :
    PagingDataAdapter<PostInfoDict, CompactPostsAdapter.CompactPostHolder>(PostInfoDictDiffCallback()) {
    class CompactPostHolder internal constructor(compactPostView: View) :
        RecyclerView.ViewHolder(compactPostView) {
            val compactArticleTitle: TextView = compactPostView.findViewById(R.id.compact_article_cell_title)
        }

    override fun onBindViewHolder(holder: CompactPostHolder, position: Int) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompactPostHolder {
        return CompactPostHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_compact_article, parent, false)
        )
    }
}
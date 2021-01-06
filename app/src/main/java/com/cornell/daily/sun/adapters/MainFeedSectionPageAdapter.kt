package com.cornell.daily.sun.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.Post
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainFeedSectionPageAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    ListAdapter<List<Post>, RecyclerView.ViewHolder>(PageDiffCallback()) {

    class PageHolder internal constructor(pageView: View) : RecyclerView.ViewHolder(pageView) {
        val mainFeedArticleRecyclerView: RecyclerView =
            pageView.findViewById(R.id.main_feed_article_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val pageView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main_feed_section_page, parent, false) as View
        return PageHolder(pageView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

private class PageDiffCallback : DiffUtil.ItemCallback<List<Post>>() {
    override fun areContentsTheSame(oldItem: List<Post>, newItem: List<Post>): Boolean {
        TODO("Not yet implemented")
    }

    override fun areItemsTheSame(oldItem: List<Post>, newItem: List<Post>): Boolean {
        TODO("Not yet implemented")
    }
}
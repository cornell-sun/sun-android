package com.cornell.daily.sun.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.PostInfoDict
import javax.inject.Singleton


@Singleton
class MainFeedSectionPageAdapter :
    ListAdapter<List<PostInfoDict>, RecyclerView.ViewHolder>(PageDiffCallback()) {

    class PageHolder internal constructor(pageView: View) : RecyclerView.ViewHolder(pageView) {
        val mainFeedArticleRecyclerView: RecyclerView =
            pageView.findViewById(R.id.main_feed_article_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val pageView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_main_feed_section_page, parent, false) as View
        return PageHolder(pageView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val articleAdapter = MainFeedArticleAdapter()
        val pageHolder = holder as PageHolder
        val page = currentList[position]
        articleAdapter.submitList(page)
        pageHolder.mainFeedArticleRecyclerView.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}

private class PageDiffCallback : DiffUtil.ItemCallback<List<PostInfoDict>>() {
    override fun areContentsTheSame(oldItem: List<PostInfoDict>, newItem: List<PostInfoDict>): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: List<PostInfoDict>, newItem: List<PostInfoDict>): Boolean {
        return oldItem === newItem
    }
}
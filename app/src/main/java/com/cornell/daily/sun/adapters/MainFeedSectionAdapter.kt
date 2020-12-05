package com.cornell.daily.sun.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.Section
import com.cornell.daily.sun.data.SectionType

class MainFeedSectionAdapter :
    ListAdapter<Section, RecyclerView.ViewHolder>(PostObjectListDiffCallback()) {

    class SectionHolder internal constructor(sectionView: View) :
        RecyclerView.ViewHolder(sectionView) {
        val sectionTitle: TextView = sectionView.findViewById(R.id.section_title)
        val topDivider: View = sectionView.findViewById(R.id.section_top_divider)
        val bottomDivider: View = sectionView.findViewById(R.id.section_bottom_divider)
        val mainFeedArticleRecyclerView: RecyclerView =
            sectionView.findViewById(R.id.main_feed_article_list)
    }

    class FeaturedArticleHolder internal constructor(featuredArticleView: View) :
        RecyclerView.ViewHolder(featuredArticleView) {
        val featuredArticleTitle: TextView =
            featuredArticleView.findViewById(R.id.featured_article_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            -1 -> {
                val sectionView = LayoutInflater.from(parent.context)
                    .inflate(
                        R.layout.list_item_main_feed_section,
                        parent,
                        false
                    ) as View
                return SectionHolder(sectionView)
            }
            else -> {
                val featuredArticleView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.main_feed_featured_article, parent, false) as View
                return FeaturedArticleHolder(featuredArticleView)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (currentList[position].sectionType == SectionType.FEATURED) {
            return SectionType.FEATURED.id
        }
        return -1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = currentList[position].sectionType
        Log.i("position", position.toString())

        when (holder.itemViewType) {
            -1 -> {
                val sectionTitle = section.title
                val posts = currentList[position].posts
                val sectionHolder = holder as SectionHolder
                sectionHolder.sectionTitle.text = sectionTitle
                val articleAdapter = MainFeedArticleAdapter()
                sectionHolder.mainFeedArticleRecyclerView.apply {
                    adapter = articleAdapter
                    layoutManager = LinearLayoutManager(context)
                }
                articleAdapter.submitList(posts)
            }
            SectionType.FEATURED.id -> {
                val post = currentList[position].posts[0]
                val featuredArticleHolder = holder as FeaturedArticleHolder
                featuredArticleHolder.featuredArticleTitle.text = post.title
            }
        }
    }
}

private class PostObjectListDiffCallback : DiffUtil.ItemCallback<Section>() {
    override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean {
        return oldItem.sectionType == newItem.sectionType
    }
}
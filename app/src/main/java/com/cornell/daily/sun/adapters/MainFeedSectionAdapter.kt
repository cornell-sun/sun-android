package com.cornell.daily.sun.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.PostInfoDict
import com.cornell.daily.sun.data.Section
import com.cornell.daily.sun.data.SectionType
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class MainFeedSectionAdapter(
    private val context: Context?,
    val selectPostFn: (PostInfoDict) -> Unit
) :
    ListAdapter<Section, RecyclerView.ViewHolder>(PostObjectListDiffCallback()) {

    class SectionHolder internal constructor(sectionView: View) :
        RecyclerView.ViewHolder(sectionView) {
        val sectionTitle: TextView = sectionView.findViewById(R.id.section_title)
        val mainFeedSectionPagerTabLayout: TabLayout =
            sectionView.findViewById(R.id.main_feed_section_pager_tab_layout)
        val mainFeedSectionViewPager: ViewPager2 =
            sectionView.findViewById(R.id.main_feed_section_pager)
        val sectionFeaturedPostTitle: TextView =
            sectionView.findViewById(R.id.section_featured_post_title)
        val sectionFeaturedPostAuthor: TextView =
            sectionView.findViewById(R.id.section_featured_post_author)
        val sectionFeaturedPostImage: ImageView =
            sectionView.findViewById(R.id.section_featured_post_image)
    }

    class FeaturedArticleHolder internal constructor(featuredArticleView: View) :
        RecyclerView.ViewHolder(featuredArticleView) {
        val featuredArticleTitle: TextView =
            featuredArticleView.findViewById(R.id.featured_post_title)
        val featuredArticleImage: ImageView =
            featuredArticleView.findViewById(R.id.featured_post_image)
        val featuredArticleAuthor: TextView =
            featuredArticleView.findViewById(R.id.featured_post_author)
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
        var post: PostInfoDict? = null
        when (holder.itemViewType) {
            -1 -> {
                val sectionTitle = section.title
                val posts = currentList[position].posts
                post = currentList[position].posts[0]
                val remainingPosts = posts.slice(1 until posts.size)
                val sectionHolder = holder as SectionHolder
                sectionHolder.sectionTitle.text = sectionTitle
                val sectionPageAdapter = MainFeedSectionPageAdapter(selectPostFn)
                sectionHolder.mainFeedSectionViewPager.apply {
                    adapter = sectionPageAdapter
                }
                TabLayoutMediator(
                    sectionHolder.mainFeedSectionPagerTabLayout,
                    sectionHolder.mainFeedSectionViewPager
                ) { tab, _ ->
                    run {
                        tab.view.isClickable = false
                    }
                }.attach()
                sectionHolder.sectionFeaturedPostTitle.text = post.title
                sectionHolder.sectionFeaturedPostAuthor.text =
                    context?.getString(R.string.byline, post.getByline())
                Picasso.get().load(post.getMediumImageUrl()).fit().centerCrop()
                    .into(sectionHolder.sectionFeaturedPostImage)
                sectionPageAdapter.submitList(remainingPosts.windowed(3, 3, partialWindows = false))
            }
            SectionType.FEATURED.id -> {
                post = currentList[position].posts[0]
                val featuredArticleHolder = holder as FeaturedArticleHolder

                featuredArticleHolder.featuredArticleTitle.text = post.title
                featuredArticleHolder.featuredArticleAuthor.text =
                    context?.getString(R.string.byline, post.getByline())
                Picasso.get().load(post.getMediumImageUrl()).fit().centerCrop()
                    .into(featuredArticleHolder.featuredArticleImage)
            }
        }
        holder.itemView.setOnClickListener {
            selectPostFn(post!!)
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
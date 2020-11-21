package com.cornell.daily.sun.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.Section

class MainFeedSectionAdapter(private var sections: MutableList<Section>) :
    RecyclerView.Adapter<MainFeedSectionAdapter.SectionHolder>() {

    class SectionHolder internal constructor(articleView: View) :
        RecyclerView.ViewHolder(articleView) {
        val sectionTitle: TextView = articleView.findViewById(R.id.section_title)
        val mainFeedArticleRecyclerView: RecyclerView = articleView.findViewById(R.id.main_feed_article_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionHolder {
        val sectionView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.list_item_main_feed_section,
                parent,
                false
            ) as View

        return SectionHolder(sectionView)
    }

    override fun onBindViewHolder(holder: SectionHolder, position: Int) {
        holder.sectionTitle.text = sections[position].title
        holder.mainFeedArticleRecyclerView.apply {
            adapter = MainFeedArticleAdapter(sections[position].articles)
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getItemCount() = sections.size
}
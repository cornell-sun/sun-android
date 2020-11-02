package com.cornell.daily.sun.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R

class MainFeedAdapter(private var articles: MutableList<String>) :
    RecyclerView.Adapter<MainFeedAdapter.ArticleViewHolder>() {

    class ArticleViewHolder internal constructor(articleView: View) :
        RecyclerView.ViewHolder(articleView) {
        val articleTitle: TextView = articleView.findViewById(R.id.article_item_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val articleView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.list_item_article, // this is our custom cell layout
                parent,
                false
            ) as View

        return ArticleViewHolder(articleView)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.articleTitle.text = articles[position]
    }

    override fun getItemCount() = articles.size
}
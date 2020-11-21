package com.cornell.daily.sun.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.Article

class MainFeedArticleAdapter(private var articles: MutableList<Article>) :
    RecyclerView.Adapter<MainFeedArticleAdapter.ArticleHolder>() {
    class ArticleHolder internal constructor(articleView: View) :
        RecyclerView.ViewHolder(articleView) {
        val articleTitle: TextView = articleView.findViewById(R.id.article_title)
        val author: TextView = articleView.findViewById(R.id.article_author)
        val articleImage: ImageView = articleView.findViewById(R.id.article_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val articleView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_main_feed_article, parent, false) as View
        return ArticleHolder(articleView)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.articleTitle.text = articles[position].title
    }

    override fun getItemCount(): Int = articles.size
}
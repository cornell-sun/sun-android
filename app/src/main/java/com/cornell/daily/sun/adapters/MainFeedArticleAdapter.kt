package com.cornell.daily.sun.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornell.daily.sun.R
import com.cornell.daily.sun.data.PostInfoDict
import com.squareup.picasso.Picasso

class MainFeedArticleAdapter :
    ListAdapter<PostInfoDict, MainFeedArticleAdapter.ArticleHolder>(PostObjectDiffCallback()) {

    class ArticleHolder internal constructor(articleView: View) :
        RecyclerView.ViewHolder(articleView) {
        val articleTitle: TextView = articleView.findViewById(R.id.article_title)
        val authorText: TextView = articleView.findViewById(R.id.article_author)
        val articleImageView: ImageView = articleView.findViewById(R.id.article_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val articleView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_main_feed_article, parent, false) as View
        return ArticleHolder(articleView)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val post = currentList[position]
        holder.articleTitle.text = currentList[position].title
        Picasso.get().load(post.getMediumImageUrl()).fit().centerCrop()
            .into(holder.articleImageView)
        holder.authorText.text = post.getByline()
        holder.itemView.setOnClickListener { view ->
            view.findNavController().navigate(R.id.main_feed_to_post)
        }
    }

}

private class PostObjectDiffCallback : DiffUtil.ItemCallback<PostInfoDict>() {
    override fun areItemsTheSame(oldItem: PostInfoDict, newItem: PostInfoDict): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostInfoDict, newItem: PostInfoDict): Boolean {
        return oldItem == newItem
    }
}
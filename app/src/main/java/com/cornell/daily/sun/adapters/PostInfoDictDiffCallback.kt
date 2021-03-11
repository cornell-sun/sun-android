package com.cornell.daily.sun.adapters

import androidx.recyclerview.widget.DiffUtil
import com.cornell.daily.sun.data.PostInfoDict

class PostInfoDictDiffCallback : DiffUtil.ItemCallback<PostInfoDict>() {
    override fun areItemsTheSame(oldItem: PostInfoDict, newItem: PostInfoDict): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PostInfoDict, newItem: PostInfoDict): Boolean {
        return oldItem == newItem
    }
}
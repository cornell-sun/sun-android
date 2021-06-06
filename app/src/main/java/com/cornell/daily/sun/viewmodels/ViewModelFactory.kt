package com.cornell.daily.sun.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cornell.daily.sun.data.PostRepository

class ViewModelFactory(private val postRepository: PostRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainFeedViewModel::class.java)) {
            MainFeedViewModel(postRepository) as T
        } else if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            PostViewModel(postRepository) as T
        } else if (modelClass.isAssignableFrom(SectionsViewModel::class.java)) {
            SectionsViewModel(postRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
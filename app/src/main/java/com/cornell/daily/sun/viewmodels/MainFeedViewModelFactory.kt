package com.cornell.daily.sun.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cornell.daily.sun.data.PostRepository

class MainFeedViewModelFactory(private val postRepository: PostRepository) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainFeedViewModel(postRepository) as T
    }
}
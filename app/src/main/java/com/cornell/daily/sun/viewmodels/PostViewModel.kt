package com.cornell.daily.sun.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cornell.daily.sun.data.PostInfoDict

class PostViewModel : ViewModel() {
    private val mutableSelectedPost = MutableLiveData<PostInfoDict>()
    val selectedPost: LiveData<PostInfoDict> get() = mutableSelectedPost

    fun selectPost(post: PostInfoDict) {
        mutableSelectedPost.value = post
    }
}
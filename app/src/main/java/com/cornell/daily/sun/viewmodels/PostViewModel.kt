package com.cornell.daily.sun.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cornell.daily.sun.data.PostInfoDict
import com.cornell.daily.sun.data.PostRepository

class PostViewModel(private val postRepository: PostRepository) : ViewModel() {

    private val mutablePostStack =
        MutableLiveData<ArrayDeque<PostInfoDict>>().apply { value = ArrayDeque() }
    val postStack: LiveData<ArrayDeque<PostInfoDict>> get() = mutablePostStack

    fun pushPost(post: PostInfoDict) {
        val clonedStack: ArrayDeque<PostInfoDict> = ArrayDeque()
        mutablePostStack.value?.toList()?.let { clonedStack.addAll(it) }
        clonedStack.addFirst(post)
        mutablePostStack.postValue(clonedStack)
    }

    fun popPost(): PostInfoDict {
        val clonedStack: ArrayDeque<PostInfoDict> = ArrayDeque()
        mutablePostStack.value?.toList()?.let { clonedStack.addAll(it) }
        val poppedPost = clonedStack.removeFirst()
        mutablePostStack.value = clonedStack
        return poppedPost
    }

    suspend fun loadPost(postID: Int) {
        val newPost = postRepository.getPost(postID)
        pushPost(newPost)
    }
}
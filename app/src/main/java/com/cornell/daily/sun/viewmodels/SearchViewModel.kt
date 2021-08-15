package com.cornell.daily.sun.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.cornell.daily.sun.data.PostRepository
import com.cornell.daily.sun.data.PostsPagingSource

class SearchViewModel(private val postRepository: PostRepository) : ViewModel() {
    private val mutableQuery: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String> get() = mutableQuery

    val flow = Pager(PagingConfig(pageSize = 20)) {
        PostsPagingSource(postRepository, section = null, query = query.value)
    }.flow.cachedIn(viewModelScope)

    fun setQuery(query: String) {
        mutableQuery.value = query
    }
}
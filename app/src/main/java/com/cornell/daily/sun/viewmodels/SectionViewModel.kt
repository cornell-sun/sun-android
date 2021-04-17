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
import com.cornell.daily.sun.data.SectionType

class SectionViewModel(private val postRepository: PostRepository) : ViewModel() {
    private val mutableSection: MutableLiveData<SectionType> = MutableLiveData()
    val section: LiveData<SectionType> get() = mutableSection

    val flow = Pager(PagingConfig(pageSize = 20)) {
        PostsPagingSource(postRepository, section.value!!)
    }.flow.cachedIn(viewModelScope)

    fun setSection(section: SectionType?) {
        this.mutableSection.value = section
    }
}
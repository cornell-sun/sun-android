package com.cornell.daily.sun.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.cornell.daily.sun.data.*

class SettingsViewModel(private val postRepository: PostRepository) : ViewModel() {

    private val mutableSectionStack =
        MutableLiveData<ArrayDeque<SettingType>>().apply { value = ArrayDeque() }
    val sectionStack: LiveData<ArrayDeque<SettingType>> get() = mutableSectionStack

}
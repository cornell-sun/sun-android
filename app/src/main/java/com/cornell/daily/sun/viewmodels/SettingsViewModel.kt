package com.cornell.daily.sun.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cornell.daily.sun.data.PostRepository
import com.cornell.daily.sun.data.SettingType

class SettingsViewModel(private val postRepository: PostRepository) : ViewModel() {

    private val mutableSectionStack =
        MutableLiveData<ArrayDeque<SettingType>>().apply { value = ArrayDeque() }
    val sectionStack: LiveData<ArrayDeque<SettingType>> get() = mutableSectionStack

}
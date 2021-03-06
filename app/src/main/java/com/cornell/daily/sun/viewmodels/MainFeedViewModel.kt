package com.cornell.daily.sun.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cornell.daily.sun.data.PostRepository
import com.cornell.daily.sun.data.Section
import com.cornell.daily.sun.data.SectionType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFeedViewModel(private val postRepository: PostRepository) :
    ViewModel() {

    val sections: MutableLiveData<List<Section>> by lazy {
        MutableLiveData<List<Section>>().also {
            GlobalScope.launch {
                loadSections()
            }
        }
    }

    suspend fun loadSections() {
        // Create featured section separately
        val sectionPosts = postRepository.getMainFeedPosts()
        val featuredSection =
            Section(SectionType.FEATURED, mutableListOf((postRepository.getFeaturedPost())))
        sectionPosts.add(0, featuredSection)
        sections.postValue(sectionPosts)
    }


}
package com.cornell.daily.sun.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cornell.daily.sun.data.PostRepository
import com.cornell.daily.sun.data.Section
import com.cornell.daily.sun.data.SectionType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainFeedViewModel @ViewModelInject constructor(private val postRepository: PostRepository) :
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
        val sectionPosts = postRepository.getSectionPosts()
        val featuredSection =
            Section(SectionType.FEATURED, mutableListOf((postRepository.getFeaturedPost())))
        val featuredPostID = featuredSection.posts[0].id
        sectionPosts.forEach { section ->
            run {
                section.filterPosts(featuredPostID)
                section.clamp(10)
            }
        }
        featuredSection.posts[0].featuredMediaImages?.full?.url?.let { Log.i("Featured: ", it) }
        sectionPosts.add(0, featuredSection)
        Log.i(
            "Section Posts Size", sectionPosts.size
                .toString()
        )
        sections.postValue(sectionPosts)
    }
}
package com.cornell.daily.sun.data

import com.cornell.daily.sun.api.SunWordpressService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import javax.inject.Inject
import javax.inject.Singleton

enum class SectionType(val id: Int, val title: String) {
    FEATURED(1, "Featured"),
    NEWS(2, "News"),
    OPINION(3, "Opinion"),
    SPORTS(4, "Sports"),
    ARTS(5, "Arts"),
    SCIENCE(6, "Science"),
    DINING(7, "Dining"),
    MULTIMEDIA(8, "Multimedia");
}

@Singleton
class PostRepository @Inject constructor(private val service: SunWordpressService) {
    suspend fun getSectionPosts(): MutableList<Section> {
        val deferredCalls: List<Deferred<Section>> =
            SectionType.values().filter { section -> section != SectionType.FEATURED }
                .map { section ->
                    GlobalScope.async {
                        Section(
                            section,
                            service.getPostsBySection(section.id, 1).take(POSTS_PER_SECTION)
                        )
                    }
                }

        return deferredCalls.awaitAll().toMutableList()
    }

    suspend fun getFeaturedPost(): PostObject {
        return service.getFeaturedPost()
    }

    companion object {
        const val POSTS_PER_SECTION = 6
    }
}
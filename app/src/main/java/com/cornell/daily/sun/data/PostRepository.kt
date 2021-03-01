package com.cornell.daily.sun.data

import com.cornell.daily.sun.api.SunWordpressService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

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

class PostRepository constructor(private val service: SunWordpressService) {
    suspend fun getSectionPosts(): MutableList<Section> {
        val featuredPostID = getFeaturedPost().id
        val deferredCalls: List<Deferred<Section>> =
            SectionType.values().filter { section -> section != SectionType.FEATURED }
                .map { section ->
                    GlobalScope.async {
                        val totalPosts = mutableListOf<PostInfoDict>()
                        var i = 1
                        while (totalPosts.size < POSTS_PER_SECTION) {
                            var posts = service.getPostsBySection(section.id, i)
                                .map { post -> post.postInfoDict }
                                .filter { post -> post.id != featuredPostID }
                            if (posts.size > POSTS_PER_SECTION - totalPosts.size) {
                                posts = posts.take(POSTS_PER_SECTION - totalPosts.size)
                            }
                            totalPosts.addAll(posts)
                            i++
                        }
                        Section(
                            section,
                            totalPosts
                        )
                    }
                }
        return deferredCalls.awaitAll().toMutableList()
    }

    suspend fun getFeaturedPost(): PostInfoDict {
        return service.getFeaturedPost().postInfoDict
    }

    suspend fun getPost(id: Int): PostInfoDict {
        return service.getPostById(id).postInfoDict
    }

    companion object {
        const val POSTS_PER_SECTION = 10
    }
}
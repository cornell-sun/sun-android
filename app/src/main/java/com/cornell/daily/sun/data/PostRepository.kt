package com.cornell.daily.sun.data

import com.cornell.daily.sun.api.SunWordpressService
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

class PostRepository constructor(private val service: SunWordpressService) {
    suspend fun getMainFeedPosts(): MutableList<Section> {
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

    suspend fun getPostsBySection(sectionType: SectionType, page: Int): List<PostInfoDict> {
        return service.getPostsBySection(sectionType.id, page).map { post -> post.postInfoDict }
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
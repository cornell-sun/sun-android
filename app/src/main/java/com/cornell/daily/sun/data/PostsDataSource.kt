package com.cornell.daily.sun.data

import androidx.paging.PagingSource
import com.cornell.daily.sun.api.SunWordpressService
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsDataSource @Inject constructor(
    private val service: SunWordpressService,
    private val sectionType: SectionType? = null
) : PagingSource<Int, Post>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val nextPageNumber = params.key ?: 0
            var posts: List<Post> = listOf()
            if (sectionType != null) {
                posts = service.getPostsBySection(sectionType.id, nextPageNumber)
            }
            LoadResult.Page(
                data = posts,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (posts.isNotEmpty()) nextPageNumber + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}
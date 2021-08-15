package com.cornell.daily.sun.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException

class PostsPagingSource(
    private val repository: PostRepository,
    private val section: SectionType?,
    private val query: String?
) :
    PagingSource<Int, PostInfoDict>() {
    override fun getRefreshKey(state: PagingState<Int, PostInfoDict>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostInfoDict> {
        try {
            var nextPageNum: Int? = (params.key ?: 0) + 1
            val res = nextPageNum?.let {
                // Precondition: Only section or query should be set at a time
                if (section != null) {
                    repository.getPostsBySection(section, it)
                } else if (query != null) {
                    repository.getPosts(query, it)
                } else {
                    emptyList()
                }
            }

            if (res!!.isEmpty()) {
                nextPageNum = null
            }
            return LoadResult.Page(
                data = res,
                prevKey = null,
                nextKey = nextPageNum
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)

        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}
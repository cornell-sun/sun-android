package com.cornell.daily.sun.api

import com.cornell.daily.sun.data.PostObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SunWordpressService {
    @GET("wp/v2/posts/{postId}")
    fun getPostById(@Path("postId") postId: Int): Call<PostObject?>?

    @GET("wp/v2/posts")
    fun getPostsById(@Query("page") page: Int): Call<List<PostObject?>?>?

    @GET("wp/v2/posts")
    suspend fun getPostsBySection(
        @Query("categories") sectionID: Int,
        @Query("page") page: Int
    ): List<PostObject>

    @GET("wp/v2/users/{authorId}")
    fun getPostsByAuthor(@Path("authorId") authorId: Int): Call<List<PostObject?>?>?

    @GET("wp/v2/media/{mediaId}")
    fun getMedia(@Path("mediaId") mediaId: Int): Call<List<PostObject?>?>?

    @GET("wp/v2/categories/{categoryId}")
    fun getCategories(@Path("categoryId") categoryId: Int): Call<List<String?>?>?

    @get:GET("sun-backend-extension/v1/trending")
    val trending: Call<List<Any?>?>?

    @GET("wp/v2/posts")
    fun searchPosts(
        @Query("search") query: String?,
        @Query("page") page: Int
    ): Call<List<PostObject?>?>?

    @GET("sun-backend-extension/v1/featured")
    suspend fun getFeaturedPost(): PostObject

    @GET("wp/v2/urltoid")
    fun urlToId(@Query("url") url: String?): Call<Int?>?
}

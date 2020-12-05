package com.cornell.daily.sun.api

import com.google.gson.Gson
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class SunWordpressInterceptor(
    private val parser: JsonParser = JsonParser(),
    private val gson: Gson = Gson()
) : Interceptor {
    private lateinit var response: Response
    override fun intercept(chain: Interceptor.Chain): Response {
        response = chain.proceed(chain.request())
        val url = response.request.url.toUrl().toString()
        if (url.contains("wp/v2/posts")) {
            return interceptPosts()
        } else if (url.contains("sun-backend-extension/v1/featured")) {
            return interceptFeaturedPost()
        }
        return response
    }

    private fun interceptPosts(): Response {
        val body = response.body
        val jsonBody = parser.parse(body?.string()).asJsonArray
        val postDicts =
            Array(jsonBody.size()) { i -> jsonBody.get(i).asJsonObject.getAsJsonObject("post_info_dict") }
        val contentType: MediaType? = response.body!!.contentType()
        val newBody = gson.toJson(postDicts).toResponseBody(contentType)
        return response.newBuilder().body(newBody).build()
    }

    private fun interceptFeaturedPost(): Response {
        val body = response.body
        val jsonBody = parser.parse(body?.string()).asJsonObject
        val postInfoDict = jsonBody.getAsJsonObject("post_info_dict")
        val contentType: MediaType? = response.body!!.contentType()
        val newBody = gson.toJson(postInfoDict).toResponseBody(contentType)
        return response.newBuilder().body(newBody).build()
    }
}
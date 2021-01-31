package com.cornell.daily.sun.util

import com.cornell.daily.sun.api.SunWordpressService
import com.cornell.daily.sun.data.PostRepository
import com.cornell.daily.sun.viewmodels.MainFeedViewModelFactory

object InjectorUtils {
    fun provideMainFeeViewModelFactory(): MainFeedViewModelFactory {
        val postRepository = PostRepository(SunWordpressService.create())
        return MainFeedViewModelFactory(postRepository)
    }
}
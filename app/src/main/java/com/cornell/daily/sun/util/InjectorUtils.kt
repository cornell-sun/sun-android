package com.cornell.daily.sun.util

import com.cornell.daily.sun.api.SunWordpressService
import com.cornell.daily.sun.data.PostRepository
import com.cornell.daily.sun.viewmodels.ViewModelFactory

object InjectorUtils {
    fun provideViewModelFactory(): ViewModelFactory {
        val postRepository = PostRepository(SunWordpressService.create())
        return ViewModelFactory(postRepository)
    }
}
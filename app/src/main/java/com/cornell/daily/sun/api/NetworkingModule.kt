package com.cornell.daily.sun.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object NetworkingModule {
    private const val BASE_URL = "https://cornellsun.com/wp-json/"

    @Provides
    fun provideSunWordpressService(client: OkHttpClient): SunWordpressService {
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(SunWordpressService::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val sunInterceptor = SunWordpressInterceptor()
        return OkHttpClient.Builder().addInterceptor(logger).addInterceptor(sunInterceptor).build()
    }
}
package com.anywhere.takehome.data.remote.di

import com.anywhere.takehome.BuildConfig
import com.anywhere.takehome.data.remote.DuckDuckGoService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

    protected open fun baseUrl() = HttpUrl.parse(BuildConfig.BASE_API_URL)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofitClient(json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    @Provides
    @Singleton
    fun provideDuckDuckGoService(retrofit: Retrofit) : DuckDuckGoService {
        return retrofit.create(DuckDuckGoService::class.java)
    }

    @Provides
    @Singleton
    fun provideKotlinSerializationJson(): Json = Json{
        ignoreUnknownKeys = true
        isLenient = true
    }

}
package com.anywhere.takehome.data.remote

import com.anywhere.takehome.data.remote.models.DuckDuckGoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DuckDuckGoService {

    @GET("/")
    suspend fun getTopics(
        @Query("q") query: String,
        @Query("format") format: String = "json"
    ): Response<DuckDuckGoResponse>
}
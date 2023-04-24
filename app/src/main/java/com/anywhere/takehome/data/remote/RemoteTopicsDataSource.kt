package com.anywhere.takehome.data.remote

import com.anywhere.takehome.base.BaseRemoteDataSource
import com.anywhere.takehome.data.remote.models.DuckDuckGoResponse
import com.anywhere.takehome.data.remote.models.RelatedTopic
import javax.inject.Inject

/**
 * Implementation of TopicsDataSource to
 * fetch Topics from the DuckDuck Go Service API
 */
class RemoteTopicsDataSource @Inject constructor(
    private val duckDuckGoService: DuckDuckGoService
    ) : BaseRemoteDataSource() {

    override suspend fun loadTopics(query: String): Result<List<RelatedTopic>>{

        val result: Result<DuckDuckGoResponse> = safeApiCall {
            duckDuckGoService.getTopics(query)
        }

        return when(result){
            is Result.Success -> Result.Success(result.data.relatedTopics)
            is Result.Error -> Result.Error(errorMessage = result.errorMessage, throwable = result.throwable)
        }
    }

}



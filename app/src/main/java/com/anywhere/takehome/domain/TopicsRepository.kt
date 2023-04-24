package com.anywhere.takehome.domain

import com.anywhere.takehome.data.TopicsDataSource
import com.anywhere.takehome.data.di.LocalDataSource
import com.anywhere.takehome.data.di.RemoteDataSource
import com.anywhere.takehome.data.remote.Result
import com.anywhere.takehome.data.remote.models.RelatedTopic
import javax.inject.Inject

interface TopicsRepository {
    suspend fun getRelatedTopics(query: String): Result<List<RelatedTopic>>
}

class TopicsRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteTopicsDataSource: TopicsDataSource,
    @LocalDataSource private val localDataSource: TopicsDataSource
) : TopicsRepository {

    override suspend fun getRelatedTopics(query: String): Result<List<RelatedTopic>> {
        return remoteTopicsDataSource.loadTopics(query)
    }
}

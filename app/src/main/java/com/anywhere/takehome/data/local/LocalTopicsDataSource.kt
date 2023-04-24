package com.anywhere.takehome.data.local

import com.anywhere.takehome.data.TopicsDataSource
import com.anywhere.takehome.data.local.database.TopicsDao
import com.anywhere.takehome.data.remote.Result
import com.anywhere.takehome.data.remote.models.RelatedTopic
import javax.inject.Inject

/**
 * Implementation of TopicsDataSource
 * To Fetch Topics from the Local Data source
 */
class LocalTopicsDataSource @Inject constructor(
    private val topicsDao: TopicsDao
) : TopicsDataSource {

    /**
     * To be implemented
     */
    override suspend fun loadTopics(query: String): Result<List<RelatedTopic>> {
        return Result.Success(emptyList())
    }
}

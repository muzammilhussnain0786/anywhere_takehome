package com.anywhere.takehome.data

import com.anywhere.takehome.data.remote.Result
import com.anywhere.takehome.data.remote.models.RelatedTopic


/**
 * A data source interface for different
 * types of data sources for topics
 */
interface TopicsDataSource {

    suspend fun loadTopics(
        query: String
    ): Result<List<RelatedTopic>>

}
package com.anywhere.takehome.data.local.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query


@Entity
data class Topic(

    @PrimaryKey(autoGenerate = false)
    val name: String,
    val text: String,
    val imageUrl: String
)

@Dao
interface TopicsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topic: Topic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(topics: List<Topic>)

    @Query("SELECT * FROM Topic")
    suspend fun getTopics(): List<Topic>
}
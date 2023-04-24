package com.anywhere.takehome.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Topic::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun topicsDao() : TopicsDao
}
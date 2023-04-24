package com.anywhere.takehome.data.di

import com.anywhere.takehome.data.TopicsDataSource
import com.anywhere.takehome.data.local.LocalTopicsDataSource
import com.anywhere.takehome.data.remote.RemoteTopicsDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Qualifier
annotation class LocalDataSource

@Qualifier
annotation class RemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @RemoteDataSource
    fun bindsRemoteDataSource(
        remoteTopicsDataSource: RemoteTopicsDataSource
    ): TopicsDataSource

    @Binds
    @LocalDataSource
    fun localDataSource(
        localTopicsDataSource: LocalTopicsDataSource
    ): TopicsDataSource

}
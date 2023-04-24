package com.anywhere.takehome.domain.di

import com.anywhere.takehome.domain.TopicsRepository
import com.anywhere.takehome.domain.TopicsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Singleton
    @Binds
    fun bindsCharacterRepository(
        topicsRepositoryImpl: TopicsRepositoryImpl
    ): TopicsRepository

}

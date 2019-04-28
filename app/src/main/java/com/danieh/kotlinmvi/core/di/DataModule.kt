package com.danieh.kotlinmvi.core.di

import com.danieh.kotlinmvi.features.data.repository.PostsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by danieh on 19/04/2019.
 */
@Module
class DataModule {

    @Provides
    @Singleton
    fun providePostsRepository(dataSource: PostsRepository.Network): PostsRepository = dataSource
}

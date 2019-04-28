package com.danieh.kotlinmvi.core.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.danieh.kotlinmvi.core.di.viewmodel.ViewModelFactory
import com.danieh.kotlinmvi.features.presentation.postdetails.PostDetailsViewModel
import com.danieh.kotlinmvi.features.presentation.posts.PostsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by danieh on 19/04/2019.
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    abstract fun bindsPostsViewModel(postsViewModel: PostsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailsViewModel::class)
    abstract fun bindsPostDetailsViewModel(postDetailsViewModel: PostDetailsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

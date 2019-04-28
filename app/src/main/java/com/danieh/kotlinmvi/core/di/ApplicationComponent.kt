package com.danieh.kotlinmvi.core.di

import com.danieh.kotlinmvi.KotlinMVIApp
import com.danieh.kotlinmvi.core.di.viewmodel.ViewModelModule
import com.danieh.kotlinmvi.features.presentation.MainActivity
import com.danieh.kotlinmvi.features.presentation.postdetails.PostDetailsFragment
import com.danieh.kotlinmvi.features.presentation.posts.PostsFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by danieh on 19/04/2019.
 */
@Singleton
@Component(modules = [ApplicationModule::class, NetworkModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(application: KotlinMVIApp)

    fun inject(mainActivity: MainActivity)

    fun inject(postsFragment: PostsFragment)

    fun inject(postDetailsFragment: PostDetailsFragment)
}

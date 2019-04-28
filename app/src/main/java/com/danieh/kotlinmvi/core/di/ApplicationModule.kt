package com.danieh.kotlinmvi.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by danieh on 19/04/2019.
 */
@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun context() = context
}

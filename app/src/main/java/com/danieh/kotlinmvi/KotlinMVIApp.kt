package com.danieh.kotlinmvi

import android.app.Application
import com.danieh.kotlinmvi.core.di.*
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by danieh on 19/04/2019.
 */
class KotlinMVIApp : Application() {

    companion object {
        var appContext: KotlinMVIApp? = null
        var BASE_URL = "http://jsonplaceholder.typicode.com/"
    }

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .networkModule(NetworkModule(BASE_URL))
            .dataModule(DataModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        this.injectMembers()
        this.initializeLeakDetection()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun injectMembers() = appComponent.inject(this)

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
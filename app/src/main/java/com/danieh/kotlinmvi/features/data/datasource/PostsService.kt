package com.danieh.kotlinmvi.features.data.datasource

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by danieh on 19/04/2019.
 */
@Singleton
class PostsService
@Inject constructor(retrofit: Retrofit) : Api {

    private val api by lazy { retrofit.create(Api::class.java) }

    override fun posts() = api.posts()

    override fun comments() = api.comments()

    override fun users() = api.users()
}

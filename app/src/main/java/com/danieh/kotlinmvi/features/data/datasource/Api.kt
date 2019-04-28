package com.danieh.kotlinmvi.features.data.datasource

import com.danieh.kotlinmvi.features.data.model.CommentEntity
import com.danieh.kotlinmvi.features.data.model.PostEntity
import com.danieh.kotlinmvi.features.data.model.UserEntity
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

/**
 * Created by danieh on 19/04/2019.
 */
interface Api {

    @GET("posts")
    fun posts(): Deferred<List<PostEntity>>

    @GET("comments")
    fun comments(): Deferred<List<CommentEntity>>

    @GET("users")
    fun users(): Deferred<List<UserEntity>>
}

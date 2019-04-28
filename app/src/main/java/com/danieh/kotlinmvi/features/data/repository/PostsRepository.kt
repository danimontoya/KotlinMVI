package com.danieh.kotlinmvi.features.data.repository

import com.danieh.kotlinmvi.features.data.datasource.PostsService
import com.danieh.kotlinmvi.features.domain.model.Comment
import com.danieh.kotlinmvi.features.domain.model.Post
import com.danieh.kotlinmvi.features.domain.model.User
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

/**
 * Created by danieh on 19/04/2019.
 */
interface PostsRepository {

    suspend fun posts(): Deferred<List<Post>>
    suspend fun comments(): Deferred<List<Comment>>
    suspend fun users(): Deferred<List<User>>

    open class Network @Inject constructor(private val service: PostsService) : PostsRepository {

        override suspend fun posts(): Deferred<List<Post>> = coroutineScope {
            async {
                service.posts().await().map { it.toPost() }
            }
        }

        override suspend fun comments(): Deferred<List<Comment>> = coroutineScope {
            async {
                service.comments().await().map { it.toComment() }
            }
        }

        override suspend fun users(): Deferred<List<User>> = coroutineScope {
            async {
                service.users().await().map { it.toUser() }
            }
        }
    }
}

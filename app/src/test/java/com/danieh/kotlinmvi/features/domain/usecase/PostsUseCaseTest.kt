package com.danieh.kotlinmvi.features.domain.usecase

import com.danieh.kotlinmvi.UnitTest
import com.danieh.kotlinmvi.core.mvi.states
import com.danieh.kotlinmvi.features.data.repository.PostsRepository
import com.danieh.kotlinmvi.features.domain.model.Post
import com.danieh.kotlinmvi.features.domain.model.User
import com.danieh.kotlinmvi.features.presentation.model.PostUserView
import com.danieh.kotlinmvi.features.presentation.posts.PostsViewState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

/**
 * Created by danieh on 27/04/2019.
 */
class PostsUseCaseTest : UnitTest() {

    companion object {
        private val POST_LIST = listOf(Post(1, 0, "title", "body"))
        private val USER_LIST = listOf(User(1, "name", "username", "email"))
        private val POST_USER_LIST = listOf(PostUserView(1, 0, "title", "body", "name"))
    }

    private lateinit var useCase: PostsUseCase

    private lateinit var postsRepository: PostsRepository

    @Before
    fun setUp() {
        val postsDeferred = async { POST_LIST }
        val usersDeferred = async { USER_LIST }
        postsRepository = mock {
            onBlocking { posts() } doReturn postsDeferred
            onBlocking { users() } doReturn usersDeferred
        }
        useCase = PostsUseCase(postsRepository)
    }

    @Test
    fun `use case should get data from reposity posts and user calls`() {
        val states = runBlocking { useCase.getPosts().states(PostsViewState.GetPostsTask.init()) }

        val loadingState = PostsViewState.GetPostsTask.loading()
        states[0] shouldEqual loadingState
        val successState = PostsViewState.GetPostsTask.success(POST_USER_LIST)
        states[1] shouldEqual successState
    }
}

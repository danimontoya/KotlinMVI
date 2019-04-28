package com.danieh.kotlinmvi.features.presentation.posts

import com.danieh.kotlinmvi.AndroidTest
import com.danieh.kotlinmvi.core.mvi.Action
import com.danieh.kotlinmvi.core.mvi.TaskStatus
import com.danieh.kotlinmvi.core.mvi.produceActions
import com.danieh.kotlinmvi.core.mvi.send
import com.danieh.kotlinmvi.features.domain.usecase.PostsUseCase
import com.danieh.kotlinmvi.features.presentation.model.PostUserView
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import timber.log.Timber

/**
 * Created by danieh on 28/04/2019.
 */
class PostsViewModelTest : AndroidTest() {

    companion object {
        private val POST_USER_LIST = listOf(PostUserView(1, 0, "title", "body", "name"))
    }

    private lateinit var postsViewModel: PostsViewModel

    private lateinit var postsUseCase: PostsUseCase

    @Before
    fun setUp() {
        val receiveChannel: ReceiveChannel<Action<PostsViewState.GetPostsTask>> = produceActions {
            send { PostsViewState.GetPostsTask.loading() }
            send { PostsViewState.GetPostsTask.success(POST_USER_LIST) }
        }
        postsUseCase = mock {
            onBlocking { getPosts() } doReturn receiveChannel
        }
        //given { runBlocking { postsUseCase.getPosts() } }.willReturn(receiveChannel)
        postsViewModel = PostsViewModel(postsUseCase)
    }

    @Test
    fun `loading posts should update state`() {

        postsViewModel.store.liveData.observeForever { result ->
            when (result) {
                is PostsViewState.GetPostsTask -> {
                    when {
                        result.status == TaskStatus.INIT -> Timber.tag("PostsFragment").d("INIT")
                        result.status == TaskStatus.SUCCESS -> POST_USER_LIST shouldEqual result.posts
                        result.status == TaskStatus.FAILURE -> result.error shouldEqual Throwable::class.java
                        result.status == TaskStatus.LOADING -> {
                            result.posts shouldEqual null
                            result.error shouldEqual null
                        }
                    }
                }
            }
        }

        runBlocking { postsViewModel.loadData() }
    }
}

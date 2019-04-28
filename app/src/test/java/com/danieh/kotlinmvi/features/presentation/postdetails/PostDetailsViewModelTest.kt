package com.danieh.kotlinmvi.features.presentation.postdetails

import com.danieh.kotlinmvi.AndroidTest
import com.danieh.kotlinmvi.core.mvi.Action
import com.danieh.kotlinmvi.core.mvi.TaskStatus
import com.danieh.kotlinmvi.core.mvi.produceActions
import com.danieh.kotlinmvi.core.mvi.send
import com.danieh.kotlinmvi.features.domain.usecase.CommentsUseCase
import com.danieh.kotlinmvi.features.presentation.model.CommentView
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
class PostDetailsViewModelTest : AndroidTest() {

    companion object {
        val COMMENT1 = CommentView(1, 0, "name", "email", "body")
        val COMMENT2 = CommentView(2, 1, "name", "email", "body")
        private val COMMENT_LIST = listOf(COMMENT1, COMMENT2)
    }

    private lateinit var postDetailsViewModel: PostDetailsViewModel

    private lateinit var commentsUseCase: CommentsUseCase

    @Before
    fun setUp() {
        val receiveChannel: ReceiveChannel<Action<PostDetailsViewState.GetPostDetailsTask>> = produceActions {
            send { PostDetailsViewState.GetPostDetailsTask.loading() }
            send { PostDetailsViewState.GetPostDetailsTask.success(COMMENT_LIST) }
        }
        commentsUseCase = mock {
            onBlocking { getComments(1) } doReturn receiveChannel
        }
        //given { runBlocking { commentsUseCase.getComments(1) } }.willReturn(receiveChannel)
        postDetailsViewModel = PostDetailsViewModel(commentsUseCase)
    }

    @Test
    fun `loading posts should update state`() {

        postDetailsViewModel.store.liveData.observeForever { result ->
            when (result) {
                is PostDetailsViewState.GetPostDetailsTask -> {
                    when {
                        result.status == TaskStatus.INIT -> Timber.tag("PostDetailsFragment").d("INIT")
                        result.status == TaskStatus.SUCCESS -> listOf(COMMENT1) shouldEqual result.comments
                        result.status == TaskStatus.FAILURE -> result.error shouldEqual Throwable::class.java
                        result.status == TaskStatus.LOADING -> {
                            result.comments shouldEqual null
                            result.error shouldEqual null
                        }
                    }
                }
            }
        }

        runBlocking { postDetailsViewModel.getComments(1) }
    }
}

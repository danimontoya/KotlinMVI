package com.danieh.kotlinmvi.features.domain.usecase

import com.danieh.kotlinmvi.UnitTest
import com.danieh.kotlinmvi.core.mvi.states
import com.danieh.kotlinmvi.features.data.repository.PostsRepository
import com.danieh.kotlinmvi.features.domain.model.Comment
import com.danieh.kotlinmvi.features.presentation.model.CommentView
import com.danieh.kotlinmvi.features.presentation.postdetails.PostDetailsViewState
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
class CommentsUseCaseTest : UnitTest() {

    companion object {
        private val COMMENT1 = Comment(1, 1, "comment name1", "email1", "body1")
        private val COMMENT2 = Comment(2, 2, "comment name2", "email2", "body2")
        private val COMMENT_LIST = listOf(COMMENT1, COMMENT2)

        private val COMMENT_VIEW_LIST = listOf(CommentView(2, 2, "comment name2", "email2", "body2"))
    }

    private lateinit var useCase: CommentsUseCase

    private lateinit var postsRepository: PostsRepository

    @Before
    fun setUp() {
        val commentsDeferred = async { COMMENT_LIST }
        postsRepository = mock {
            onBlocking { comments() } doReturn commentsDeferred
        }
        useCase = CommentsUseCase(postsRepository)
    }

    @Test
    fun `use case should get data from reposity posts and user calls`() {
        val states = runBlocking { useCase.getComments(2).states(PostDetailsViewState.GetPostDetailsTask.init()) }

        val loadingState = PostDetailsViewState.GetPostDetailsTask.loading()
        states[0] shouldEqual loadingState
        val successState = PostDetailsViewState.GetPostDetailsTask.success(COMMENT_VIEW_LIST)
        states[1] shouldEqual successState
    }
}

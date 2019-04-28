package com.danieh.kotlinmvi.features.data.repository

import com.danieh.kotlinmvi.UnitTest
import com.danieh.kotlinmvi.features.data.datasource.PostsService
import com.danieh.kotlinmvi.features.data.model.CommentEntity
import com.danieh.kotlinmvi.features.data.model.PostEntity
import com.danieh.kotlinmvi.features.domain.model.Comment
import com.danieh.kotlinmvi.features.domain.model.Post
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

/**
 * Created by danieh on 23/04/2019.
 */
class PostsRepositoryTest : UnitTest() {

    companion object {
        private val POST_ENTITY_LIST = listOf(PostEntity(0, 0, "title", "body"))
        private val POST_LIST = listOf(Post(0, 0, "title", "body"))

        private val COMMENT_ENTITY_LIST = listOf(CommentEntity(0, 0, "comment name", "email", "body"))
        private val COMMENT_LIST = listOf(Comment(0, 0, "comment name", "email", "body"))
    }

    private lateinit var repository: PostsRepository.Network

    @Mock
    private lateinit var service: PostsService

    @Before
    fun setUp() {
        repository = PostsRepository.Network(service)
    }

    @Test
    fun `should get post list from service`() {
        val postDeferred = async { POST_ENTITY_LIST }
        given { service.posts() }.willReturn(postDeferred)

        val posts = runBlocking { repository.posts().await() }

        posts shouldEqual POST_LIST
        verify(service).posts()
    }

    @Test
    fun `should get comment list from service`() {
        val commentDeferred = async { COMMENT_ENTITY_LIST }
        given { service.comments() }.willReturn(commentDeferred)

        val comments = runBlocking { repository.comments().await() }

        comments shouldEqual COMMENT_LIST
        verify(service).comments()
    }
}

package com.danieh.kotlinmvi.features.domain.usecase

import com.danieh.kotlinmvi.core.mvi.Action
import com.danieh.kotlinmvi.core.mvi.produceActions
import com.danieh.kotlinmvi.core.mvi.send
import com.danieh.kotlinmvi.features.data.repository.PostsRepository
import com.danieh.kotlinmvi.features.presentation.model.PostUserView
import com.danieh.kotlinmvi.features.presentation.model.PostView
import com.danieh.kotlinmvi.features.presentation.posts.PostsViewState
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject

/**
 * Created by danieh on 24/04/2019.
 */
class PostsUseCase @Inject constructor(private val postsRepository: PostsRepository) {

    fun getPosts(): ReceiveChannel<Action<PostsViewState.GetPostsTask>> = produceActions {
        send { PostsViewState.GetPostsTask.loading() }
        try {
            val posts = postsRepository.posts()
                .await()
                .map { it.toPostView() }
            val users = postsRepository.users().await().map { it.toUserView() }

            val listPostUserView = mutableListOf<PostUserView>()
            for (postView: PostView in posts) {
                val userView = users.find { postView.userId == it.id }
                val postUserView = PostUserView(
                    postView.userId, postView.id, postView.title, postView.body, userView?.name
                        ?: "Unknown"
                )
                listPostUserView.add(postUserView)
            }

            send { PostsViewState.GetPostsTask.success(listPostUserView) }

        } catch (e: Exception) {
            send { PostsViewState.GetPostsTask.failure(e) }
        }
    }
}

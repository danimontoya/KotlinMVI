package com.danieh.kotlinmvi.features.domain.usecase

import com.danieh.kotlinmvi.core.mvi.Action
import com.danieh.kotlinmvi.core.mvi.produceActions
import com.danieh.kotlinmvi.core.mvi.send
import com.danieh.kotlinmvi.features.data.repository.PostsRepository
import com.danieh.kotlinmvi.features.presentation.postdetails.PostDetailsViewState
import kotlinx.coroutines.channels.ReceiveChannel
import javax.inject.Inject

/**
 * Created by danieh on 24/04/2019.
 */
class CommentsUseCase @Inject constructor(private val postsRepository: PostsRepository) {

    fun getComments(postId: Int): ReceiveChannel<Action<PostDetailsViewState.GetPostDetailsTask>> =
        produceActions {
            send { PostDetailsViewState.GetPostDetailsTask.loading() }
            try {
                val comments = postsRepository.comments().await().map { it.toCommentView() }
                send { PostDetailsViewState.GetPostDetailsTask.success(comments.filter { postId == it.postId }) }

            } catch (e: Exception) {
                send { PostDetailsViewState.GetPostDetailsTask.failure(e) }
            }
        }
}

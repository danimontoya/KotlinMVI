package com.danieh.kotlinmvi.features.presentation.postdetails

import com.danieh.kotlinmvi.core.mvi.TaskStatus
import com.danieh.kotlinmvi.features.presentation.model.CommentView

/**
 * Created by danieh on 27/04/2019.
 */
sealed class PostDetailsViewState {

    data class GetPostDetailsTask(
        val status: TaskStatus = TaskStatus.INIT,
        val comments: List<CommentView>? = null,
        val error: Throwable? = null
    ) {

        companion object {

            internal fun init(): GetPostDetailsTask {
                return GetPostDetailsTask(TaskStatus.INIT, emptyList())
            }

            internal fun success(comments: List<CommentView>?): GetPostDetailsTask {
                return GetPostDetailsTask(TaskStatus.SUCCESS, comments)
            }

            internal fun failure(error: Throwable?): GetPostDetailsTask {
                return GetPostDetailsTask(TaskStatus.FAILURE, null, error = error)
            }

            internal fun loading(): GetPostDetailsTask {
                return GetPostDetailsTask(TaskStatus.LOADING)
            }
        }
    }
}

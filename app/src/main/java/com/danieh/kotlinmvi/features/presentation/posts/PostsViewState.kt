package com.danieh.kotlinmvi.features.presentation.posts

import com.danieh.kotlinmvi.core.mvi.TaskStatus
import com.danieh.kotlinmvi.features.presentation.model.PostUserView

/**
 * Created by danieh on 27/04/2019.
 */
sealed class PostsViewState {

    data class GetPostsTask(
        val status: TaskStatus = TaskStatus.INIT,
        val posts: List<PostUserView>? = null,
        val error: Throwable? = null
    ) {

        companion object {

            internal fun init(): GetPostsTask {
                return GetPostsTask(TaskStatus.INIT, emptyList())
            }

            internal fun success(posts: List<PostUserView>?): GetPostsTask {
                return GetPostsTask(TaskStatus.SUCCESS, posts)
            }

            internal fun failure(error: Throwable?): GetPostsTask {
                return GetPostsTask(TaskStatus.FAILURE, null, error = error)
            }

            internal fun loading(): GetPostsTask {
                return GetPostsTask(TaskStatus.LOADING)
            }
        }
    }
}

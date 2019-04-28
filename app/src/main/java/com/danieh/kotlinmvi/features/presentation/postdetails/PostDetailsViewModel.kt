package com.danieh.kotlinmvi.features.presentation.postdetails

import androidx.lifecycle.ViewModel
import com.danieh.kotlinmvi.core.mvi.ViewStateStore
import com.danieh.kotlinmvi.features.domain.usecase.CommentsUseCase
import javax.inject.Inject

/**
 * Created by danieh on 19/04/2019.
 */
class PostDetailsViewModel @Inject constructor(private val useCase: CommentsUseCase) : ViewModel() {

    val store = ViewStateStore(PostDetailsViewState.GetPostDetailsTask.init())

    fun getComments(postId: Int) = store.dispatchActions(useCase.getComments(postId))

    override fun onCleared() {
        store.cancel()
    }
}

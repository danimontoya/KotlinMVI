package com.danieh.kotlinmvi.features.presentation.posts

import androidx.lifecycle.ViewModel
import com.danieh.kotlinmvi.core.mvi.ViewStateStore
import com.danieh.kotlinmvi.features.domain.usecase.PostsUseCase
import com.danieh.kotlinmvi.features.presentation.model.PostUserView
import javax.inject.Inject

/**
 * Created by danieh on 24/04/2019.
 */
class PostsViewModel @Inject constructor(private val useCase: PostsUseCase) : ViewModel() {

    val store = ViewStateStore(PostsViewState.GetPostsTask.init())

    fun loadData() = store.dispatchActions(useCase.getPosts())

    override fun onCleared() {
        store.cancel()
    }
}

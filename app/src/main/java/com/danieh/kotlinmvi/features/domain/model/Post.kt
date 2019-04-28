package com.danieh.kotlinmvi.features.domain.model

import com.danieh.kotlinmvi.features.presentation.model.PostView

data class Post(
    private val userId: Int,
    private val id: Int,
    private val title: String,
    private val body: String
) {
    fun toPostView() = PostView(userId, id, title, body)

    companion object {
        fun empty() = Post(0, 0, "", "")
    }

}
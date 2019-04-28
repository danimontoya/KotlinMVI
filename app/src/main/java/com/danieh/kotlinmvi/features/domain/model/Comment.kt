package com.danieh.kotlinmvi.features.domain.model

import com.danieh.kotlinmvi.features.presentation.model.CommentView

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
) {
    fun toCommentView() = CommentView(postId, id, name, email, body)

    companion object {
        fun empty() = Comment(0, 0, "", "", "")
    }
}

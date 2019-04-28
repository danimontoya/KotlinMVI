package com.danieh.kotlinmvi.features.presentation.postdetails

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danieh.kotlinmvi.R
import com.danieh.kotlinmvi.core.extension.inflate
import com.danieh.kotlinmvi.features.presentation.model.CommentView
import kotlinx.android.synthetic.main.layout_row_comment.view.*
import javax.inject.Inject

/**
 * Created by danieh on 20/04/2019.
 */
class CommentsAdapter @Inject constructor() : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    internal var collection = mutableListOf<CommentView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.layout_row_comment))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(commentView: CommentView) {
            itemView.comment_name.text = commentView.name
            itemView.comment_body.text = commentView.body
            itemView.comment_email.text = commentView.email
        }
    }
}
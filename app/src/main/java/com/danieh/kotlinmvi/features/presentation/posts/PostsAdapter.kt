package com.danieh.kotlinmvi.features.presentation.posts

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danieh.kotlinmvi.R
import com.danieh.kotlinmvi.core.extension.inflate
import com.danieh.kotlinmvi.features.presentation.model.PostUserView
import kotlinx.android.synthetic.main.layout_row_post.view.*
import javax.inject.Inject

/**
 * Created by danieh on 20/04/2019.
 */
class PostsAdapter @Inject constructor() : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {

    internal var collection = mutableListOf<PostUserView>()
    internal var clickListener: (PostUserView, TextView) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.layout_row_post))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(postUserView: PostUserView, clickListener: (PostUserView, TextView) -> Unit) {
            itemView.setOnClickListener { clickListener(postUserView, itemView.post_title) }
            itemView.post_title.text = postUserView.title
            itemView.post_author.text = postUserView.userName
        }
    }
}
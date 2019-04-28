package com.danieh.kotlinmvi.features.presentation.postdetails

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.danieh.kotlinmvi.R
import com.danieh.kotlinmvi.core.exception.Failure
import com.danieh.kotlinmvi.core.extension.gone
import com.danieh.kotlinmvi.core.extension.viewModel
import com.danieh.kotlinmvi.core.extension.visible
import com.danieh.kotlinmvi.core.mvi.TaskStatus
import com.danieh.kotlinmvi.core.platform.BaseFragment
import com.danieh.kotlinmvi.features.presentation.model.CommentView
import com.danieh.kotlinmvi.features.presentation.postdetails.PostDetailsFragmentArgs.fromBundle
import com.danieh.kotlinmvi.features.presentation.widget.empty.EmptyListener
import com.danieh.kotlinmvi.features.presentation.widget.error.ErrorListener
import kotlinx.android.synthetic.main.fragment_post_details.*
import kotlinx.android.synthetic.main.fragment_posts.view_empty
import kotlinx.android.synthetic.main.fragment_posts.view_error
import timber.log.Timber
import javax.inject.Inject


class PostDetailsFragment : BaseFragment() {

    override fun layoutId() = R.layout.fragment_post_details

    private lateinit var viewModel: PostDetailsViewModel

    private val postId by lazy {
        arguments?.let { fromBundle(it).postId }
    }

    private val postTitle by lazy {
        arguments?.let { fromBundle(it).postTitle }
    }

    private val postBody by lazy {
        arguments?.let { fromBundle(it).postBody }
    }

    private val postAuthor by lazy {
        arguments?.let { fromBundle(it).postAuthor }
    }

    @Inject
    lateinit var commentsAdapter: CommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        val transition = TransitionInflater.from(activity).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = ChangeBounds().apply {
            enterTransition = transition
        }

        viewModel = viewModel(viewModelFactory) {
            store.observe(this@PostDetailsFragment) { result ->
                when (result) {
                    is PostDetailsViewState.GetPostDetailsTask -> {
                        when {
                            result.status == TaskStatus.INIT -> Timber.tag("PostDetailsFragment").d("INIT")
                            result.status == TaskStatus.SUCCESS -> showComments(result.comments)
                            result.status == TaskStatus.FAILURE -> showError(Failure.ServerError(result.error))
                            result.status == TaskStatus.LOADING -> showLoading()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupViewListeners()

        post_title.text = postTitle
        post_body.text = postBody
        post_author.text = postAuthor

        recycler_comments.layoutManager = LinearLayoutManager(context)
        recycler_comments.adapter = commentsAdapter
        recycler_comments.addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))

        postId?.let { getComments(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                fragmentManager?.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getComments(postId: Int) {
        viewModel.getComments(postId)
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            view_empty.gone()
            postId?.let { getComments(it) }
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            view_error.gone()
            postId?.let { getComments(it) }
        }
    }

    private fun showComments(comments: List<CommentView>?) {
        view_error.gone()
        progress_comments.gone()
        if (comments != null && comments.isNotEmpty()) {
            comments.toCollection(commentsAdapter.collection)
            commentsAdapter.notifyDataSetChanged()
            recycler_comments.visible()
            TransitionManager.beginDelayedTransition(post_details_root)
            post_comments.text =
                String.format(getString(R.string.post_details_comments_count), commentsAdapter.itemCount.toString())
        } else {
            view_empty.visible()
        }
    }

    private fun showError(failure: Failure?) {
        progress_comments.gone()
        view_error.visible()
        notify("ServerError")
    }

    private fun showLoading() {
        view_empty.gone()
        view_error.gone()
        recycler_comments.gone()
        progress_comments.visible()
    }
}

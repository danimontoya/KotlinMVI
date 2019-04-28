package com.danieh.kotlinmvi.features.presentation.posts

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.danieh.kotlinmvi.R
import com.danieh.kotlinmvi.core.exception.Failure
import com.danieh.kotlinmvi.core.extension.gone
import com.danieh.kotlinmvi.core.extension.viewModel
import com.danieh.kotlinmvi.core.extension.visible
import com.danieh.kotlinmvi.core.mvi.TaskStatus.*
import com.danieh.kotlinmvi.core.platform.BaseFragment
import com.danieh.kotlinmvi.features.presentation.model.PostUserView
import com.danieh.kotlinmvi.features.presentation.widget.empty.EmptyListener
import com.danieh.kotlinmvi.features.presentation.widget.error.ErrorListener
import kotlinx.android.synthetic.main.fragment_posts.*
import timber.log.Timber
import javax.inject.Inject

class PostsFragment : BaseFragment() {

    override fun layoutId() = R.layout.fragment_posts

    private lateinit var viewModel: PostsViewModel

    @Inject
    lateinit var postsAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        viewModel = viewModel(viewModelFactory) {
            store.observe(this@PostsFragment) { result ->
                when (result) {
                    is PostsViewState.GetPostsTask -> {
                        when {
                            result.status == INIT -> Timber.tag("PostsFragment").d("INIT")
                            result.status == SUCCESS -> onGetPostsSuccess(result.posts)
                            result.status == FAILURE -> showError(Failure.ServerError(result.error))
                            result.status == LOADING -> showLoading()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewListeners()

        recycler_posts.layoutManager = LinearLayoutManager(context)
        recycler_posts.adapter = postsAdapter

        postsAdapter.clickListener = { postView, textView ->

            val extras = FragmentNavigatorExtras(
                textView to getString(R.string.transition_post_to_postdetail)
            )
            val navDirections = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment().apply {
                postId = postView.id
                postTitle = postView.title
                postBody = postView.body
                postAuthor = postView.userName
            }
            findNavController().navigate(navDirections, extras)
        }

        viewModel.loadData()
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            view_empty.gone()
            viewModel.loadData()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            view_error.gone()
            viewModel.loadData()
        }
    }

    private fun onGetPostsSuccess(posts: List<PostUserView>?) {
        view_error.gone()
        progress_posts.gone()
        if (posts != null && posts.isNotEmpty()) {
            posts.toCollection(postsAdapter.collection)
            postsAdapter.notifyDataSetChanged()
            recycler_posts.visible()
        } else {
            view_empty.visible()
        }
    }

    private fun showError(failure: Failure?) {
        progress_posts.gone()
        view_error.visible()
        notify("ServerError")
    }

    private fun showLoading() {
        view_empty.gone()
        view_error.gone()
        recycler_posts.gone()
        progress_posts.visible()
    }
}

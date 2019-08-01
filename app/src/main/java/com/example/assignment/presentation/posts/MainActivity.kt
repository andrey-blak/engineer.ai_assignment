package com.example.assignment.presentation.posts

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.data.Api
import com.example.assignment.data.ApiFactory
import com.example.assignment.presentation.utils.toObservable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
	private lateinit var api: Api
	private lateinit var postsAdapter: PostsAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		initApi()
		initPostsList()
		updateSelectedPostsCount()
		loadPosts()
	}

	private fun initApi() {
		api = ApiFactory().createApi()
	}

	private fun initPostsList() {
		postsAdapter = PostsAdapter()
		main_posts_recycler.adapter = postsAdapter
		main_posts_recycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

		postsAdapter.setOnItemSelectionChangedListener {
			updateSelectedPostsCount()
		}
	}

	private fun loadPosts() {
		PostsDataSourceFactory(api)
			.toObservable(PAGE_SIZE, PAGE_SIZE)
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ pagedList ->
				postsAdapter.submitList(pagedList)
			}, { error ->
				Log.e(POSTS_LOG_TAG, "", error)
			})
	}

	private fun updateSelectedPostsCount() {
		/*
		 * "4. The nav bar should display the number of the selected posts."
		 * In Android a navigation bar contains the device navigation controls: Back, Home, and Overview buttons.
		 * (see https://material.io/design/platform-guidance/android-bars.html#android-navigation-bar)
		 * Taking into account that navigation bar cannot display numbers, I've assumed that "nav bar" in the assignment means the toolbar.
		 */
		val selectedCount = postsAdapter.getSelectedItemsCount()
		main_toolbar.setTitle(selectedCount.toString())
	}

	companion object {
		const val POSTS_LOG_TAG = "posts"
		private const val PAGE_SIZE = 20
	}
}

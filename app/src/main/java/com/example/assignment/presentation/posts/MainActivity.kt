package com.example.assignment.presentation.posts

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.data.Api
import com.example.assignment.data.ApiFactory
import com.example.assignment.data.dto.Post
import com.example.assignment.presentation.utils.SelectableItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
	}

	override fun onStart() {
		super.onStart()
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
		api.getPosts(POSTS_TAG, FIRST_PAGE)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ response ->
				val posts = convertPosts(response.hits)
				postsAdapter.setItems(posts)
			}, { error ->
				Log.e(LOG_TAG, "", error)
			})
	}

	private fun convertPosts(posts: List<Post>): List<SelectableItem<Post>> {
	  return posts.map(this@MainActivity::convertPost)
	}

	private fun convertPost(post: Post): SelectableItem<Post> {
	  return SelectableItem(post, false)
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
		private const val LOG_TAG = "posts"
		private const val POSTS_TAG = "story"
		private const val FIRST_PAGE = 1
	}
}

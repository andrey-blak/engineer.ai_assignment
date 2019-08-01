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

	companion object {
		private const val LOG_TAG = "posts"
		private const val POSTS_TAG = "story"
		private const val FIRST_PAGE = 1
	}
}

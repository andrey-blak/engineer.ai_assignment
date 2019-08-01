package com.example.assignment.presentation.posts

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.assignment.data.Api
import com.example.assignment.data.dto.Post
import com.example.assignment.data.responses.GetPostsResponse

class PostsDataSource(
	private val api: Api
) : PageKeyedDataSource<Int, Post>() {

	override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Post>) {
		api.getPosts(POSTS_TAGS, FIRST_PAGE)
			.subscribe({ response ->
				val posts = response.hits
				val nextPage = getNextPageNumber(FIRST_PAGE, response)
				callback.onResult(posts, null, nextPage)
			}, { error ->
				onError(error)
			})
	}

	override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
		val page = params.key
		load(page, callback, params, ::getNextPageNumber)
	}

	override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
		val page = params.key
		if (page < FIRST_PAGE) {
			return
		}
		load(page, callback, params, ::getPreviousPageNumber)
	}

	private fun load(page: Int, callback: LoadCallback<Int, Post>, params: LoadParams<Int>, pageNumberChanger: (page: Int, response: GetPostsResponse) -> Int?) {
		api.getPosts(POSTS_TAGS, page)
			.subscribe({ response ->
				val posts = response.hits
				val adjacentPage = if (posts.size >= params.requestedLoadSize) {
					pageNumberChanger(page, response)
				} else {
					null
				}
				callback.onResult(posts, adjacentPage)
			}, { error ->
				onError(error)
			})
	}

	private fun onError(error: Throwable?) {
		Log.e(MainActivity.POSTS_LOG_TAG, "", error)
	}

	private fun getPreviousPageNumber(page: Int, response: GetPostsResponse): Int? {
		return page - 1
	}

	private fun getNextPageNumber(page: Int, response: GetPostsResponse): Int? {
		return if (page - FIRST_PAGE + 1 < response.totalHits) {
			page + 1
		} else {
			null
		}
	}

	companion object {
		private const val POSTS_TAGS = "story"
		private const val FIRST_PAGE = 1
	}
}

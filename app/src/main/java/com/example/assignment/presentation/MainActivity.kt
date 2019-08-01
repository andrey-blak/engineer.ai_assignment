package com.example.assignment.presentation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.example.assignment.R
import com.example.assignment.data.Api
import com.example.assignment.data.ApiFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : Activity() {
	private lateinit var api: Api

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		initApi()
	}

	override fun onStart() {
		super.onStart()
		loadPosts()
	}

	private fun initApi() {
		api = ApiFactory().createApi()
	}

	private fun loadPosts() {
		api.getPosts(POSTS_TAG, FIRST_PAGE)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ response ->
				Log.v(LOG_TAG, "")
			}, { error ->
				Log.e(LOG_TAG, "", error)
			})
	}

	companion object {
		private const val LOG_TAG = "posts"
		private const val POSTS_TAG = "story"
		private const val FIRST_PAGE = 1
	}
}

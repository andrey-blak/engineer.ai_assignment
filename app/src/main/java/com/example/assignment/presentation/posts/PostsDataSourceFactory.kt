package com.example.assignment.presentation.posts

import androidx.paging.DataSource
import com.example.assignment.data.Api
import com.example.assignment.data.dto.Post
import com.example.assignment.presentation.utils.SelectableItem

class PostsDataSourceFactory(
	private val api: Api
) : DataSource.Factory<Int, SelectableItem<Post>>() {
	override fun create(): DataSource<Int, SelectableItem<Post>> {
		return PostsDataSource(api)
			.map { post ->
				SelectableItem(post, false)
			}
	}
}

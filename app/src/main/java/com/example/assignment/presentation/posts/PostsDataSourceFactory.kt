package com.example.assignment.presentation.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.assignment.data.Api
import com.example.assignment.data.dto.Post
import com.example.assignment.presentation.utils.SelectableItem

class PostsDataSourceFactory(
	private val api: Api
) : DataSource.Factory<Int, SelectableItem<Post>>() {
	private val dataSourceLiveData = MutableLiveData<DataSource<Int, SelectableItem<Post>>>()

	override fun create(): DataSource<Int, SelectableItem<Post>> {
		val dataSource = PostsDataSource(api)
			.map { post ->
				SelectableItem(post, false)
			}
		dataSourceLiveData.postValue(dataSource)
		return dataSource
	}

	fun getDataSource(): LiveData<DataSource<Int, SelectableItem<Post>>> {
		return dataSourceLiveData
	}
}

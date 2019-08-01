package com.example.assignment.data

import com.example.assignment.data.responses.GetPostsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
	@GET("search_by_date")
	fun getPosts(
		@Query("tags") tags: String,
		@Query("page") page: Int
	): Observable<GetPostsResponse>
}

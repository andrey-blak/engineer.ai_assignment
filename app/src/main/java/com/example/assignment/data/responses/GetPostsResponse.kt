package com.example.assignment.data.responses

import com.example.assignment.data.dto.Post
import com.google.gson.annotations.SerializedName

class GetPostsResponse {
	@SerializedName("hits")
	val hits: List<Post> = ArrayList()

	@SerializedName("nbHits")
	val totalHits: Int = 0
}

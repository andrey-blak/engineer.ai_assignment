package com.example.assignment.data.dto

import com.google.gson.annotations.SerializedName

class Post {
	@SerializedName("title")
	val title: String? = null

	@SerializedName("created_at")
	val createdAt: String? = null
}

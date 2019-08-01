package com.example.assignment.presentation.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.data.dto.Post
import kotlinx.android.synthetic.main.post_item.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
	private val posts: MutableList<Post> = mutableListOf()

	override fun getItemCount(): Int {
		return posts.size
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val context = parent.context
		val inflater = LayoutInflater.from(context)
		val layoutId = ViewHolder.getLayoutResId()
		val view = inflater.inflate(layoutId, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val post = posts[position]
		holder.bind(post)
	}

	fun setItems(posts: List<Post>) {
		this.posts.clear()
		this.posts.addAll(posts)
		notifyDataSetChanged()
	}

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		companion object {
			@LayoutRes
			fun getLayoutResId() = R.layout.post_item
		}

		fun bind(post: Post) {
			itemView.post_title.text = post.title
			itemView.post_created_at.text = post.createdAt
		}
	}
}


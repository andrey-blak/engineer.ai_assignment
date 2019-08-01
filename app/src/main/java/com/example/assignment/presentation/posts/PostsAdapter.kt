package com.example.assignment.presentation.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.data.dto.Post
import com.example.assignment.presentation.utils.SelectableItem
import kotlinx.android.synthetic.main.post_item.view.*

class PostsAdapter : RecyclerView.Adapter<PostsAdapter.ViewHolder>() {
	private val posts: MutableList<SelectableItem<Post>> = mutableListOf()

	override fun getItemCount(): Int {
		return posts.size
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val context = parent.context
		val inflater = LayoutInflater.from(context)
		val layoutId = R.layout.post_item
		val view = inflater.inflate(layoutId, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val post = posts[position]
		holder.bind(post)
	}

	fun setItems(posts: List<SelectableItem<Post>>) {
		this.posts.clear()
		this.posts.addAll(posts)
		notifyDataSetChanged()
	}

	private fun onItemSelectionChanged(position: Int, isChecked: Boolean) {
		val post = posts[position]
		post.isSelected = isChecked
		notifyItemChanged(position)
	}

	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		init {
			itemView.setOnClickListener {
				itemView.post_switch.performClick()
			}
			itemView.post_switch.setOnCheckedChangeListener { buttonView, isChecked ->
				onItemSelectionChanged(adapterPosition, isChecked)
			}
		}

		fun bind(item: SelectableItem<Post>) {
			val post = item.value
			itemView.post_title.text = post.title
			itemView.post_created_at.text = post.createdAt
			itemView.post_switch.setCheckedSilently(item.isSelected)
		}
	}
}


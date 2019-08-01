package com.example.assignment.presentation.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.data.dto.Post
import com.example.assignment.presentation.utils.SelectableItem
import kotlinx.android.synthetic.main.post_item.view.*

class PostsAdapter : PagedListAdapter<SelectableItem<Post>, PostsAdapter.ViewHolder>(diffCallback) {
	private var itemSelectionChangedListener: (() -> Unit)? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val context = parent.context
		val inflater = LayoutInflater.from(context)
		val layoutId = R.layout.post_item
		val view = inflater.inflate(layoutId, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val post = getItem(position)
		if (post != null) {
			holder.bind(post)
		}
	}

	override fun submitList(pagedList: PagedList<SelectableItem<Post>>?) {
		super.submitList(pagedList)
		notifyItemSelectionChangeListener()
	}

	fun setOnItemSelectionChangedListener(listener: () -> Unit) {
		this.itemSelectionChangedListener = listener
	}

	fun getSelectedItemsCount(): Int {
		val currentList = currentList
		return if (currentList != null) {
			currentList.count { item ->
				item.isSelected
			}
		} else {
			0
		}
	}

	private fun onItemSelectionChanged(position: Int, isChecked: Boolean) {
		val post = getItem(position)
		if (post == null) {
			return
		}

		post.isSelected = isChecked
		notifyItemChanged(position)
		notifyItemSelectionChangeListener()
	}

	private fun notifyItemSelectionChangeListener() {
		itemSelectionChangedListener?.invoke()
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

	companion object {
		private val diffCallback = object : DiffUtil.ItemCallback<SelectableItem<Post>>() {
			override fun areItemsTheSame(oldItem: SelectableItem<Post>, newItem: SelectableItem<Post>): Boolean {
				return oldItem === newItem
			}

			override fun areContentsTheSame(oldItem: SelectableItem<Post>, newItem: SelectableItem<Post>): Boolean {
				return oldItem == newItem
			}
		}
	}
}

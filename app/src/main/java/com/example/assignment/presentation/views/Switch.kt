package com.example.assignment.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Switch

class Switch : Switch {
	private var onCheckedChangeListener: OnCheckedChangeListener? = null

	constructor(context: Context)
			: super(context)

	constructor(context: Context, attrs: AttributeSet)
			: super(context, attrs)

	constructor(context: Context, attrs: AttributeSet, defStyle: Int)
			: super(context, attrs, defStyle)

	override fun setOnCheckedChangeListener(listener: OnCheckedChangeListener?) {
		super.setOnCheckedChangeListener(listener)
		this.onCheckedChangeListener = listener
	}

	/**
	 * Changes the checked state without notifying the listener set in setOnCheckedChangeListener()
	 */
	fun setCheckedSilently(checked: Boolean) {
		super.setOnCheckedChangeListener(null)
		setChecked(checked)
		super.setOnCheckedChangeListener(onCheckedChangeListener)
	}
}

@file:JvmName("PagingUtils")

package com.example.assignment.presentation.utils

import androidx.paging.Config
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import io.reactivex.Observable

/**
 * Simpler version of [androidx.paging.toObservable] method from paging-rxjava2-ktx library, but allows to set initialLoadSizeHint
 */
fun <T : DataSource.Factory<Key, Value>, Key, Value> T.toObservable(
	pageSize: Int,
	initialLoadSizeHint: Int
): Observable<PagedList<Value>> {
	val builder = RxPagedListBuilder(
		this,
		Config(
			pageSize = pageSize,
			initialLoadSizeHint = initialLoadSizeHint
		)
	)
	return builder.buildObservable()
}

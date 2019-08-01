package com.example.assignment.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {
	fun createApi(): Api {
		val client = createOkhttpClient()

		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(client)
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.addConverterFactory(GsonConverterFactory.create())
			.build().create(Api::class.java)
	}

	private fun createOkhttpClient(): OkHttpClient {
		val logging = HttpLoggingInterceptor()
		logging.level = HttpLoggingInterceptor.Level.BODY

		val client = OkHttpClient.Builder()
			.addInterceptor(logging)
			.build()
		return client
	}

	companion object {
		private const val BASE_URL = "https://hn.algolia.com/api/v1/"
	}
}

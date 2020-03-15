package com.example.crime.Retrofit2

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


class NetworkService private constructor() {
    private val mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getJSONApi(): ApiService {
        return mRetrofit.create(ApiService::class.java)
    }

    private object HOLDER {
        val INSTANCE = NetworkService()
    }


    companion object {
        private var mInstance: NetworkService? = null
        private val BASE_URL = "https://jsonplaceholder.typicode.com"
        val instance: NetworkService by lazy { HOLDER.INSTANCE }
    }
}
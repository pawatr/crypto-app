package com.pawat.cryptoapp.data.remote

import com.pawat.cryptoapp.common.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    var endpoints: ApiEndpoints

    companion object {

        private var INSTANCE: ApiManager? = null

        fun getInstance() : ApiManager = INSTANCE ?: synchronized(this) {
            INSTANCE ?: ApiManager()
        }
    }

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.HOST_NAME)
            .client(client)
            .build()

        endpoints = retrofit.create(ApiEndpoints::class.java)
    }
}
package com.pawat.cryptoapp.data.remote

import com.pawat.cryptoapp.data.remote.dto.CoinDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoints {

    @GET("/api/v3/coins/markets")
    fun getCoinList(@Query("vs_currency") vs_currency: String,
                    @Query("per_page") size: Int,
                    @Query("page") page: Int): Call<List<CoinDto>>
}
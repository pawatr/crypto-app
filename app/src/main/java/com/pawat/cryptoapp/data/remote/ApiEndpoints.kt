package com.pawat.cryptoapp.data.remote

import com.pawat.cryptoapp.data.remote.dto.CoinDetailDto
import com.pawat.cryptoapp.data.remote.dto.CoinDto
import com.pawat.cryptoapp.data.remote.dto.SearchCoinDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndpoints {

    @GET("/api/v3/coins/markets")
    fun getCoinList(@Query("vs_currency") vs_currency: String,
                    @Query("per_page") size: Int,
                    @Query("page") page: Int): Call<List<CoinDto>>

    @GET("/api/v3/search")
    fun searchCoins(@Query("query") search: String) : Call<SearchCoinDto>

    @GET("/api/v3/coins/{coinID}")
    fun getDetail(@Path("coinID") coinId: String) : Call<CoinDetailDto>
}
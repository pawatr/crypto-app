package com.pawat.cryptoapp.data.repository

import com.pawat.cryptoapp.data.remote.Result
import com.pawat.cryptoapp.data.remote.responseParser
import com.pawat.cryptoapp.common.Constants
import com.pawat.cryptoapp.data.remote.ApiEndpoints
import com.pawat.cryptoapp.data.remote.dto.CoinDto
import com.pawat.cryptoapp.data.remote.dto.SearchCoinDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CoinRepository(private val apiEndpoints: ApiEndpoints) {

    suspend fun searchCoin(search: String): Result<SearchCoinDto, Exception> {
        val response = withContext(Dispatchers.IO) {
            apiEndpoints.searchCoins(search).execute()
        }
        return responseParser(response)
    }

    suspend fun getCoinList(size: Int, page: Int): Result<List<CoinDto>, Exception> {
        val response = withContext(Dispatchers.IO) {
            apiEndpoints.getCoinList(Constants.QUERY_CURRENCY , size, page).execute()
        }
        return responseParser(response)
    }
}
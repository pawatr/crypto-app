package com.pawat.cryptoapp.data.remote.dto

import com.pawat.cryptoapp.data.model.Coin

data class SearchCoinDto(
    val coins: List<CoinSearch>
)

data class CoinSearch(
    val id: String,
    val name: String?,
    val symbol: String?,
    val large: String?
)

fun CoinSearch.toCoin(): Coin {
    return Coin(
        id = id,
        name = name ?: "",
        image = large ?: "",
        symbol = symbol ?: "",
        currentPrice = 0.0,
        priceChangePercentageTwentyFourHour = 0.0)
}
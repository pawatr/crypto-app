package com.pawat.cryptoapp.data.remote.dto

data class SearchCoinDto(
    val coins: List<CoinSearch>
)

data class CoinSearch(
    val id: String,
    val name: String,
    val symbol: String,
    val large: String
)
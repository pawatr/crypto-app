package com.pawat.cryptoapp.data.model

data class Coin(
    val id: String,
    val image: String,
    val symbol: String,
    val name: String,
    val priceChangePercentageTwentyFourHour: Double,
    val currentPrice: Double,
)
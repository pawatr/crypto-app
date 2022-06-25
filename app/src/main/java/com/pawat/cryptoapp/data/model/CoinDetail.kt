package com.pawat.cryptoapp.data.model

data class CoinDetail(
    val id: String,
    val description: String,
    val image: String,
    val name: String,
    val symbol: String,
    val currentPrice: String,
    val priceChange: Double,
    val tradeUrl: String
)
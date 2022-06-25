package com.pawat.cryptoapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.pawat.cryptoapp.data.model.CoinDetail

data class CoinDetailDto(
    val description: Description,
    val id: String,
    val image: Image,
    val name: String,
    val symbol: String,
    @SerializedName("market_data")
    val marketData: MarketData,
    val tickers: List<Ticker> = arrayListOf()
)

data class Image(
    val thumb: String,
    val small: String,
    val large: String
)

data class Description(
    val en: String
)

data class MarketData(
    @SerializedName("current_price")
    val currentPrice: CurrentPrice,
    @SerializedName("price_change_percentage_24h")
    val priceChange: Double
)

data class CurrentPrice(
    val thb: String
)

data class Ticker(
    @SerializedName("trade_url")
    val tradeUrl: String
)

fun CoinDetailDto.toCoinDetail(): CoinDetail {
    return CoinDetail(
        id = id,
        description = description.en,
        image = image.large,
        name = name,
        symbol = symbol,
        currentPrice = marketData.currentPrice.thb,
        priceChange = marketData.priceChange,
        tradeUrl = tickers[0].tradeUrl
    )
}
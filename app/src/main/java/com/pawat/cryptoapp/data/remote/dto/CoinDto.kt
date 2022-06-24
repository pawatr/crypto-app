package com.pawat.cryptoapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.pawat.cryptoapp.data.model.Coin

data class CoinDto(
    val ath: Double?,
    @SerializedName("ath_change_percentage")
    val athChangePercentage: Double?,
    @SerializedName("ath_date")
    val athDate: String?,
    val atl: Double?,
    @SerializedName("atl_change_percentage")
    val atlChangePercentage: Double?,
    @SerializedName("atl_date")
    val atlDate: String?,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double?,
    @SerializedName("current_price")
    val currentPrice: Double?,
    @SerializedName("fully_diluted_valuation")
    val fullyDilutedValuation: Double?,
    @SerializedName("high_24h")
    val highTwentyFourHour: Double?,
    val id: String,
    val image: String?,
    @SerializedName("last_updated")
    val lastUpdated: String?,
    @SerializedName("low_24h")
    val lowTwentyFourHour: Double?,
    @SerializedName("market_cap")
    val marketCap: Double?,
    @SerializedName("market_cap_change_24h")
    val marketCapChangeTwentyFourHour: Double?,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentageTwentyFourHour: Double?,
    @SerializedName("market_cap_rank")
    val marketCapRank: Double?,
    @SerializedName("max_supply")
    val maxSupply: Double?,
    val name: String?,
    @SerializedName("price_change_24h")
    val priceChangeTwentyFourHour: Double?,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentageTwentyFourHour: Double?,
    val symbol: String?,
    @SerializedName("total_supply")
    val totalSupply: Double?,
    @SerializedName("total_volume")
    val totalVolume: Double?
)

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        name = name ?: "",
        image = image ?: "",
        symbol = symbol ?: "",
        currentPrice = currentPrice ?: 0.0,
        priceChangePercentageTwentyFourHour = priceChangePercentageTwentyFourHour ?: 0.0
    )
}
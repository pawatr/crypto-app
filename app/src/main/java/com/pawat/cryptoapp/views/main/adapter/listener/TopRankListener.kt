package com.pawat.cryptoapp.views.main.adapter.listener

import com.pawat.cryptoapp.data.model.Coin

interface TopRankListener {
    fun onTopRankCoinClickListener(coin: Coin)
}
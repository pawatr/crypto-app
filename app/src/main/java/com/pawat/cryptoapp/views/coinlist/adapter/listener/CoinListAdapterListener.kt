package com.pawat.cryptoapp.views.coinlist.adapter.listener

import com.pawat.cryptoapp.data.model.Coin

interface CoinListAdapterListener {
    fun onScrollToBottomListener()
    fun onCoinClickListener(coin: Coin)
    fun onInviteClickListener()
}
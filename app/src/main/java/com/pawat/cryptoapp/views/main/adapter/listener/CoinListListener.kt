package com.pawat.cryptoapp.views.main.adapter.listener

import com.pawat.cryptoapp.data.model.Coin

interface CoinListListener {
    fun onEditorActionListener(search: String)
    fun onTextChangedToEmpty()
    fun onCoinClickListener(coin: Coin)
    fun onInviteClickListener()
    fun onScrollToBottomListener()
}
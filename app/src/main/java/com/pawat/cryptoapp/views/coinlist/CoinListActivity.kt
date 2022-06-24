package com.pawat.cryptoapp.views.coinlist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pawat.cryptoapp.R

class CoinListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_list)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setIcon(R.drawable.ic_blockchain)
        }

    }
}
package com.pawat.cryptoapp.views.coinlist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pawat.cryptoapp.R
import com.pawat.cryptoapp.data.model.Coin
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinListActivity: AppCompatActivity() {

    private val coinListViewModel: CoinListViewModel by viewModel()
    private var coinList: ArrayList<Coin> = arrayListOf()
    private var page = 1

    companion object{
        const val TAG = "CoinListActivity"
        const val SIZE_PER_GET = 15
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_list)
        observerData()
        coinListViewModel.getCoinList(SIZE_PER_GET, page)
    }

    private fun observerData() {
        coinListViewModel.coins.observe(this){
            coinList.addAll(it)
        }
        coinListViewModel.error.observe(this){
            Log.d(TAG, it)
        }
        coinListViewModel.loading.observe(this){
            Log.d(TAG, it.toString())
        }
    }
}
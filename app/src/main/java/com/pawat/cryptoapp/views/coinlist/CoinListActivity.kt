package com.pawat.cryptoapp.views.coinlist

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import com.pawat.cryptoapp.R
import com.pawat.cryptoapp.data.model.Coin
import com.pawat.cryptoapp.data.remote.dto.CoinSearch
import kotlinx.android.synthetic.main.activity_coin_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CoinListActivity: AppCompatActivity() {

    private val coinListViewModel: CoinListViewModel by viewModel()
    private val searchViewModel: SearchViewModel by viewModel()

    private var coinList: ArrayList<Coin> = arrayListOf()
    private var page = 1

    private var coinSearchList: ArrayList<CoinSearch> = arrayListOf()

    companion object{
        const val TAG = "CoinListActivity"
        const val SIZE_PER_GET = 15
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_list)
        setupView()
        observerData()
        coinListViewModel.getCoinList(SIZE_PER_GET, page)
    }

    private fun setupView() {
        searchEdt.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val search = searchEdt.text.toString()
                if (search.trim().isNotEmpty()) {
                    searchViewModel.searchCoin(searchEdt.text.toString())
                }
                searchEdt.clearFocus()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun observerData() {
        //region {@Observer CoinList}
        coinListViewModel.coins.observe(this){
            coinList.addAll(it)
        }
        coinListViewModel.error.observe(this){
            // error get coin list
            Log.d(TAG, it)
        }
        coinListViewModel.loading.observe(this){
            // loading
            Log.d(TAG, it.toString())
        }
        //endregion

        //region {@Observer Search}
        searchViewModel.coins.observe(this){
            coinSearchList.addAll(it)
        }
        searchViewModel.error.observe(this){
            // error search coin list
            Log.d(TAG, it)
        }
        searchViewModel.loading.observe(this){
            // loading
            Log.d(TAG, it.toString())
        }
        //endregion
    }
}
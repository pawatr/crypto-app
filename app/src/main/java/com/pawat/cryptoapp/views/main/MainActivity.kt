package com.pawat.cryptoapp.views.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawat.cryptoapp.common.Constants
import com.pawat.cryptoapp.data.model.Coin
import com.pawat.cryptoapp.data.remote.dto.CoinSearch
import com.pawat.cryptoapp.databinding.ActivityMainBinding
import com.pawat.cryptoapp.views.detail.CoinDetailViewModel
import com.pawat.cryptoapp.views.main.adapter.CoinListAdapter
import com.pawat.cryptoapp.views.main.adapter.listener.CoinListListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.pow

class MainActivity: AppCompatActivity(), CoinListListener {

    private val searchViewModel: SearchViewModel by viewModel()
    private val coinListViewModel: CoinListViewModel by viewModel()
    private val coinDetailViewModel: CoinDetailViewModel by viewModel()


    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val coinListAdapter: CoinListAdapter by lazy { CoinListAdapter() }

    private var coinList: ArrayList<Coin> = arrayListOf()
    private var page = 1
    private var coinSearchList: ArrayList<CoinSearch> = arrayListOf()

    companion object{
        const val TAG = "CoinListActivity"
        const val GET_COIN_SIZE_PER_PAGE = 15
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        observerData()
        coinListViewModel.getCoinList(GET_COIN_SIZE_PER_PAGE, page)
    }

    //region {@Observer CoinListListener}
    override fun onEditorActionListener(search: String) {
        searchViewModel.searchCoin(search)
    }

    override fun onTextChangedToEmpty() {
        page = 1
        coinList.clear()
        coinListViewModel.getCoinList(GET_COIN_SIZE_PER_PAGE, page)
    }

    override fun onCoinClickListener(coin: Coin) {
        coinDetailViewModel.getCoinDetail(coin.id)
    }

    override fun onInviteClickListener() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
        intent.putExtra(Intent.EXTRA_TEXT, Constants.INVITE_HOST_NAME)
        startActivity(Intent.createChooser(intent, "Share URL"))
    }

    override fun onScrollToBottomListener() {
        page += 1
        coinListViewModel.getCoinList(GET_COIN_SIZE_PER_PAGE, page)
    }
    //endregion

    private fun setupView() {
        coinListAdapter.setListener(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = coinListAdapter
        }
    }

    private fun observerData() {
        coinListViewModel.coins.observe(this){
            coinList.addAll(it)
            updateViewCoinList()
        }
        coinListViewModel.error.observe(this){
            // error get coin list
            Log.d(TAG, it)
        }
        coinListViewModel.loading.observe(this){
            // loading
            Log.d(TAG, it.toString())
        }

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

        coinDetailViewModel.coinDetail.observe(this){
            Log.d("CoinDetail", "${it.name}")
        }
        coinDetailViewModel.error.observe(this){
            // error search coin list
            Log.d(TAG, it)
        }
        coinDetailViewModel.loading.observe(this){
            // loading
            Log.d(TAG, it.toString())
        }
    }

    private fun updateViewCoinList() {
        val items: ArrayList<Any> = arrayListOf()
        var n = 0
        var inviteIndex = getIndex(n)
        for (i in 0 until coinList.size){
            if (i == inviteIndex){
                n += 1
                items.add(Constants.INVITE_FRIEND_VIEW)
                inviteIndex = getIndex(n) + i
            }
            items.add(coinList[i])
        }
        items.add(0, Constants.HEADER_VIEW)
        items.add(0, Constants.SEARCH_VIEW)
        coinListAdapter.items = items
    }

    private fun getIndex(n: Int): Int{
        return 3 * (2.0.pow(n.toDouble()).toInt())
    }
}
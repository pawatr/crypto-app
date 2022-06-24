package com.pawat.cryptoapp.views.coinlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawat.cryptoapp.R
import com.pawat.cryptoapp.common.Constants
import com.pawat.cryptoapp.data.model.Coin
import com.pawat.cryptoapp.data.remote.dto.CoinSearch
import com.pawat.cryptoapp.views.coinlist.adapter.CoinListAdapter
import com.pawat.cryptoapp.views.coinlist.adapter.listener.CoinListAdapterListener
import kotlinx.android.synthetic.main.activity_coin_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class CoinListActivity: AppCompatActivity(), CoinListAdapterListener {

    private val coinListViewModel: CoinListViewModel by viewModel()
    private val searchViewModel: SearchViewModel by viewModel()

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
        setContentView(R.layout.activity_coin_list)
        setupView()
        observerData()
        coinListViewModel.getCoinList(GET_COIN_SIZE_PER_PAGE, page)
    }

    //region {@Observer CoinList}
    override fun onScrollToBottomListener() {
        page += 1
        coinListViewModel.getCoinList(GET_COIN_SIZE_PER_PAGE, page)
    }

    override fun onCoinClickListener(coin: Coin) {
        //TODO call detail
    }

    override fun onInviteClickListener() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
        intent.putExtra(Intent.EXTRA_TEXT, Constants.INVITE_HOST_NAME)
        startActivity(Intent.createChooser(intent, "Share URL"))
    }
    //endregion

    private fun setupView() {
        coinListAdapter.setListener(this)
        coinRecycler?.apply {
            layoutManager = LinearLayoutManager(this@CoinListActivity)
            adapter = coinListAdapter
        }
        searchEdt?.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
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
        searchEdt?.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()){
                page = 1
                coinList.clear()
                coinListViewModel.getCoinList(GET_COIN_SIZE_PER_PAGE, page)
                searchEdt.setText("")
                searchEdt.clearFocus()
            }
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
    }

    private fun updateViewCoinList() {
        val items: java.util.ArrayList<Any> = arrayListOf()
        var inviteIndex = 3
        var i = 1
        while (i <= coinList.size) {
            if (i == inviteIndex) {
                items.add(getString(R.string.invite_friend))
                inviteIndex *= 2
            } else {
                items.add(coinList[i - 1])
            }
            ++i
        }
        coinListAdapter.items = items
    }
}
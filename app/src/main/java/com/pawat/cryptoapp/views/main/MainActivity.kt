package com.pawat.cryptoapp.views.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.pawat.cryptoapp.common.Constants
import com.pawat.cryptoapp.data.model.Coin
import com.pawat.cryptoapp.data.remote.dto.CoinSearch
import com.pawat.cryptoapp.databinding.ActivityMainBinding
import com.pawat.cryptoapp.views.detail.CoinDetailViewModel
import com.pawat.cryptoapp.views.main.adapter.CoinListAdapter
import com.pawat.cryptoapp.views.main.adapter.TopRankAdapter
import com.pawat.cryptoapp.views.main.adapter.listener.CoinListListener
import com.pawat.cryptoapp.views.main.adapter.listener.TopRankListener
import com.pawat.cryptoapp.views.main.fragment.CoinDetailBottomSheet
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.pow

class MainActivity: AppCompatActivity(), CoinListListener, TopRankListener {

    private val searchViewModel: SearchViewModel by viewModel()
    private val coinListViewModel: CoinListViewModel by viewModel()
    private val coinDetailViewModel: CoinDetailViewModel by viewModel()


    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val topRankAdapter: TopRankAdapter by lazy { TopRankAdapter() }
    private val coinListAdapter: CoinListAdapter by lazy { CoinListAdapter(topRankAdapter) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    private var coinList: ArrayList<Coin> = arrayListOf()
    private var page = 1
    private var coinSearchList: ArrayList<CoinSearch> = arrayListOf()

    companion object{
        const val TAG = "CoinListActivity"
        const val TAG_BOTTOM_SHEET_FRAGMENT = "CoinDetailBottomSheet"
        const val GET_COIN_SIZE_PER_PAGE = 15
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        observerData()
        coinListViewModel.getCoinList(GET_COIN_SIZE_PER_PAGE, page)
    }

    //region {TopRankCoinListListener}
    override fun onTopRankCoinClickListener(coin: Coin) {
        coinDetailViewModel.getCoinDetail(coin.id)
    }
    //endregion

    //region {CoinListListener}
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
        topRankAdapter.setListener(this)
        coinListAdapter.setListener(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = coinListAdapter
        }
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetContainer)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    val bottomSheetFragment = supportFragmentManager.findFragmentByTag(TAG_BOTTOM_SHEET_FRAGMENT)
                    if (bottomSheetFragment != null) {
                        supportFragmentManager.beginTransaction()
                            .remove(bottomSheetFragment)
                            .commit()
                    }
                }
            }
        })
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
            showBottomSheet(CoinDetailBottomSheet(it))
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
        val topCoin: List<Coin> = coinList.subList(0, 3)
        val coinItems: List<Coin> = coinList.subList(3, coinList.size - 1)
        var n = 0
        var inviteIndex = getIndex(n)
        for (i in coinItems.indices){
            if (i == inviteIndex){
                n += 1
                items.add(Constants.INVITE_FRIEND_VIEW)
                inviteIndex = getIndex(n) + i
            }
            items.add(coinItems[i])
        }
        val topRankItems: ArrayList<Any> = arrayListOf()
        topRankItems.addAll(topCoin)
        topRankItems.add(Constants.OPEN_WEB_VIEW)
        items.add(0, Constants.HEADER_VIEW)
        items.add(0, topRankItems)
        items.add(0, Constants.SEARCH_VIEW)
        coinListAdapter.items = items
    }

    private fun getIndex(n: Int): Int{
        return 3 * (2.0.pow(n.toDouble()).toInt())
    }

    private fun showBottomSheet(fragment: Fragment) {
        val layoutParams: CoordinatorLayout.LayoutParams = binding.bottomSheetContainer.layoutParams
                as CoordinatorLayout.LayoutParams
        layoutParams.anchorGravity = Gravity.BOTTOM
        binding.bottomSheetContainer.layoutParams = layoutParams
        supportFragmentManager.beginTransaction()
            .replace(binding.bottomSheetContainer.id, fragment, TAG_BOTTOM_SHEET_FRAGMENT)
            .commit()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}
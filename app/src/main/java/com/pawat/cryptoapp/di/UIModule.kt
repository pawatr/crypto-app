package com.pawat.cryptoapp.di

import com.pawat.cryptoapp.views.coinlist.CoinListViewModel
import com.pawat.cryptoapp.views.coinlist.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object UIModule {
    val settingModule = module {
//        viewModel { TopCoinsViewModel(get(), get()) }
        viewModel { CoinListViewModel(get()) }
//        viewModel { CoinDetailViewModel(get()) }
        viewModel { SearchViewModel(get()) }
    }
}
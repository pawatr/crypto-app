package com.pawat.cryptoapp.di

import com.pawat.cryptoapp.data.remote.ApiManager
import com.pawat.cryptoapp.data.repository.CoinRepository
import org.koin.dsl.module

object DataModule {
    val repoModule = module {
        factory { CoinRepository(get()) }
    }
    val remoteModule = module {
        factory { ApiManager.getInstance().endpoints }
    }
}
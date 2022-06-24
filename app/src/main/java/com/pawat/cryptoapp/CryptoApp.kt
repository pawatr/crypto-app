package com.pawat.cryptoapp

import android.app.Application
import com.pawat.cryptoapp.di.DataModule
import com.pawat.cryptoapp.di.UIModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module

class CryptoApp: Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private val listModules: ArrayList<Module> by lazy {
        arrayListOf(UIModule.settingModule, DataModule.repoModule, DataModule.remoteModule)
    }

    private fun setupKoin() {
        GlobalContext.startKoin {
            androidContext(this@CryptoApp)
            modules(listModules)
        }
    }
}
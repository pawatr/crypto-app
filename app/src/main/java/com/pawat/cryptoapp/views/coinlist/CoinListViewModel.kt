package com.pawat.cryptoapp.views.coinlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawat.cryptoapp.data.model.Coin
import com.pawat.cryptoapp.data.remote.Err
import com.pawat.cryptoapp.data.remote.Loading
import com.pawat.cryptoapp.data.remote.Ok
import com.pawat.cryptoapp.data.remote.dto.toCoin
import com.pawat.cryptoapp.data.repository.CoinRepository
import kotlinx.coroutines.launch

class CoinListViewModel(private val repository: CoinRepository): ViewModel() {

    private val _coins = MutableLiveData<List<Coin>>()
    val coins: LiveData<List<Coin>> get() = _coins

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getCoinList(size: Int, page: Int) {
        viewModelScope.launch {
            when (val result = repository.getCoinList(size, page)) {
                is Ok -> {
                    val coinList = result.value.map { it.toCoin() }
                    _loading.postValue(false)
                    _coins.postValue(coinList)
                }
                is Err -> {
                    _loading.postValue(false)
                    _error.postValue(result.error.message ?: "An unexpected error")
                }
                is Loading -> {
                    _loading.postValue(true)
                }
            }
        }
    }
}
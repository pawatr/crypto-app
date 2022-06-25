package com.pawat.cryptoapp.views.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawat.cryptoapp.data.remote.Err
import com.pawat.cryptoapp.data.remote.Loading
import com.pawat.cryptoapp.data.remote.Ok
import com.pawat.cryptoapp.data.remote.dto.CoinSearch
import com.pawat.cryptoapp.data.repository.CoinRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: CoinRepository): ViewModel() {

    private val _coins = MutableLiveData<List<CoinSearch>>()
    val coins: LiveData<List<CoinSearch>> get() = _coins

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun searchCoin(search: String) {
        viewModelScope.launch {
            when (val result = repository.searchCoin(search)) {
                is Ok -> {
                    val coinList = result.value.coins
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
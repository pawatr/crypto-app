package com.pawat.cryptoapp.views.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pawat.cryptoapp.data.model.CoinDetail
import com.pawat.cryptoapp.data.remote.Err
import com.pawat.cryptoapp.data.remote.Loading
import com.pawat.cryptoapp.data.remote.Ok
import com.pawat.cryptoapp.data.remote.dto.toCoinDetail
import com.pawat.cryptoapp.data.repository.CoinRepository
import kotlinx.coroutines.launch

class CoinDetailViewModel(private val repository: CoinRepository): ViewModel() {

    private val _coinDetail = MutableLiveData<CoinDetail>()
    val coinDetail: LiveData<CoinDetail> get() = _coinDetail

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun getCoinDetail(coinId: String) {
        viewModelScope.launch {
            when (val result = repository.getCoinDetail(coinId)) {
                is Ok -> {
                    val coinDetail = result.value.toCoinDetail()
                    _loading.postValue(false)
                    _coinDetail.postValue(coinDetail)
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
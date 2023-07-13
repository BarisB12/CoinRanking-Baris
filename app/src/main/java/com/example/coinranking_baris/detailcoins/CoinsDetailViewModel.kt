package com.example.coinranking_baris.detailcoins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.repository.CoinRepository
import kotlinx.coroutines.launch

class CoinsDetailViewModel: ViewModel() {

    private var coinDetailRepository = CoinRepository()
    private var _coinDetailList = MutableLiveData<List<Coin>>()

    val coinDetailList: LiveData<List<Coin>> = _coinDetailList

    init {
        viewModelScope.launch {
            val coins = coinDetailRepository.getAllCoins()
            coins?.let {
                _coinDetailList.value = coins.data.coins
            }
        }
    }
}
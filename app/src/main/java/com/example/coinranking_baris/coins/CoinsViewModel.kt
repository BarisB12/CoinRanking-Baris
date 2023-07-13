package com.example.coinranking_baris.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.model.Coins
import com.example.coinranking_baris.repository.CoinRepository
import kotlinx.coroutines.launch

class CoinsViewModel: ViewModel() {
    private var coinRepository = CoinRepository()
    private var _coinList = MutableLiveData<List<Coin>>()

    val coinList: LiveData<List<Coin>> = _coinList

    init {
        viewModelScope.launch {
            val coins = coinRepository.getAllCoins()
            coins?.let {
                _coinList.value = coins.data.coins
            }
        }
    }
}

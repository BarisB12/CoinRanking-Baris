package com.example.coinranking_baris.coins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.repository.CoinRepository
import kotlinx.coroutines.launch

class CoinsViewModel: ViewModel() {
    private var coinRepository = CoinRepository()
    private var _coinList = MutableLiveData<List<Coin>>()
    private val _selectedSortOption = MutableLiveData<String>()

    val coinList: LiveData<List<Coin>> = _coinList
    init {
        viewModelScope.launch {
            try {
                val coins = coinRepository.getAllCoins()
                coins?.let {
                    _coinList.value = coins.data.coin
                }
            } catch (e: Exception) {
                println("Handle Exception $e")
            }
        }
    }
    fun applySortOption(sortOption: String) {
        _selectedSortOption.value = sortOption

        val currentCoinList = _coinList.value
        if (currentCoinList != null) {
            val sortedList = when (sortOption) {
                "Price - High to Low" -> currentCoinList.sortedByDescending { it.price.toDoubleOrNull() }
                "Price - Low to High" -> currentCoinList.sortedBy { it.price.toDoubleOrNull() }
                "Change - High to Low" -> currentCoinList.sortedByDescending { it.change.toDoubleOrNull() }
                "Change - Low to High" -> currentCoinList.sortedBy { it.change.toDoubleOrNull() }
                else -> currentCoinList
            }
            _coinList.value = sortedList
        }
    }
}

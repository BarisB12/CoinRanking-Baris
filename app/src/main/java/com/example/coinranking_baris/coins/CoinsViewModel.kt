package com.example.coinranking_baris.coins

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.repository.CoinsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CoinsViewModel: ViewModel() {
    private var coinRepository = CoinsRepository()
    private val _selectedSortOption = MutableLiveData<String>()

    private var _coinList = MutableLiveData<List<Coin>>()
    val coinList: LiveData<List<Coin>> = _coinList

    private var _uiState = MutableLiveData(true)
    val uiState: LiveData<Boolean> = _uiState
    init {
        callApi {  }
    }

    private fun callApi(finishAction: () -> Unit) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            println("Handle Exception $throwable")
        }
        viewModelScope.launch(exceptionHandler) {
            try {

                Log.i("CALL","coins CALL initiated")
                val coins = coinRepository.getAllCoins()
                coins.let {
                    _coinList.value = coins.data.coin
                }
                finishAction()
                Log.i("CALL","coins GOT")
                _uiState.value = false
            } catch (e: Exception) {
                _uiState.value = false
                println("Handle Exception $e")
            }
        }
    }

    /**
     * refreshes the page list content
     * */
    fun refresh(sortOption: String) {
        callApi{applySortOption(sortOption)}
    }

    fun applySortOption(sortOption: String) {
        Log.i("CALL","applySortOption")
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

    companion object {
        val SORT_OPTIONS = listOf("1","2")
    }
}

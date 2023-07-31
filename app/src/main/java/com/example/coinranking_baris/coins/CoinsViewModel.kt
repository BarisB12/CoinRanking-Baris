package com.example.coinranking_baris.coins

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinranking_baris.R
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.repository.CoinsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CoinsViewModel : ViewModel() {
    private var coinRepository = CoinsRepository()

    private var _coinList = MutableLiveData<List<Coin>>()
    val coinList: LiveData<List<Coin>> = _coinList

    private var _uiState = MutableLiveData(true)
    val uiState: LiveData<Boolean> = _uiState

    init {
        callApi({})
    }

    private fun callApi(finishAction: () -> Unit, sortOption: SortOption? = null) {
        _uiState.value = true

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            println("Handle Exception $throwable")
        }
        viewModelScope.launch(exceptionHandler) {
            try {
                Log.i("CALL", "coins CALL initiated")

                val coins = coinRepository.getCoinsWithSortOption(
                    sortOption?.sortType,
                    sortOption?.sortDirection
                )
                coins.let {
                    _coinList.value = coins.data.coin
                }
                finishAction()
                Log.i("CALL", "coins GOT")
            } catch (e: Exception) {
                println("Handle Exception $e")
            } finally {
                _uiState.value = false
            }
        }
    }

    /**
     * refreshes the page list content
     * */
    fun refresh(sortOption: SortOption?) {
        callApi({}, sortOption)
    }

    private fun applySortOption(selectedOptionId: Int) {
        Log.i("CALL", "applySortOption")

        val currentCoinList = _coinList.value
        if (currentCoinList != null) {
            val sortedList = when (selectedOptionId) {
                SORT_OPTIONS[0] -> currentCoinList.sortedByDescending { it.price.toDoubleOrNull() }
                SORT_OPTIONS[1] -> currentCoinList.sortedBy { it.price.toDoubleOrNull() }
                SORT_OPTIONS[2] -> currentCoinList.sortedByDescending { it.change.toDoubleOrNull() }
                SORT_OPTIONS[3] -> currentCoinList.sortedBy { it.change.toDoubleOrNull() }
                else -> currentCoinList
            }
            _coinList.value = sortedList
        }
    }

    companion object {
        val SORT_OPTIONS = listOf(
            R.string.price_high_to_low_label,
            R.string.price_low_to_high_label,
            R.string.change_high_to_low_label,
            R.string.change_low_to_high_label
        )
        val SORT_OPTS = listOf(
            SortOption("price", "desc"),
            SortOption("price", "asc"),
            SortOption("change", "desc"),
            SortOption("change", "asc")
        )
    }

    data class SortOption(val sortType: String, val sortDirection: String)
}

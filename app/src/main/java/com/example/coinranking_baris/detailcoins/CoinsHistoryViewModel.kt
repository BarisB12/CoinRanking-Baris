package com.example.coinranking_baris.detailcoins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinranking_baris.model.HistoryX
import com.example.coinranking_baris.repository.CoinsHistoryRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class CoinsHistoryViewModel : ViewModel() {
    private val coinsHistoryRepository = CoinsHistoryRepository()
    private val _historyCoins = MutableLiveData<List<HistoryX>>()
    val historyCoins: LiveData<List<HistoryX>> get() = _historyCoins

    init {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            println("Handle Exception $throwable")
        }
        viewModelScope.launch(exceptionHandler) {
            try {
                val historyList = coinsHistoryRepository.getAllHistoryCoins()
                _historyCoins.value = historyList.data.history
            } catch (e: Exception) {
                println("Handle Exception $e")
            }
        }
    }
}
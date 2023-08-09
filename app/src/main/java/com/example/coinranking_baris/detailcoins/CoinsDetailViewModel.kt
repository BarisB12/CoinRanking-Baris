package com.example.coinranking_baris.detailcoins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.repository.CoinsDetailRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

    class CoinsDetailViewModel : ViewModel() {
        private val coinsDetailRepository = CoinsDetailRepository()
        private val _detailCoins = MutableLiveData<List<Coin>>()
        val detailCoins: LiveData<List<Coin>> get() = _detailCoins

        init {
            val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                println("Handle Exception $throwable")
            }
            viewModelScope.launch(exceptionHandler) {
                try {
                    val detailList = coinsDetailRepository.getAllDetailCoins()
                    _detailCoins.value = detailList.data.coin
                } catch (e: Exception) {
                    println("Handle Exception $e")
                }
            }
        }
        }

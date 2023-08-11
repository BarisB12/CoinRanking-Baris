package com.example.coinranking_baris.detailcoins

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.model.CoinDetailResponse
import com.example.coinranking_baris.repository.CoinsDetailRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

    class CoinsDetailViewModel : ViewModel() {
        private val coinsDetailRepository = CoinsDetailRepository()
        private val _coinDetailLiveData = MutableLiveData<CoinDetailResponse.Data.CoinDetail?>()
        val coinDetailLiveData: LiveData<CoinDetailResponse.Data.CoinDetail?> get() = _coinDetailLiveData

        init {

        }

        fun getDetail(uuid: String) {
            val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                println("Handle Exception $throwable")
            }
            viewModelScope.launch(exceptionHandler) {
                try {
                    val coinDetailResponse = coinsDetailRepository.getCoinDetail(uuid)
                    if (coinDetailResponse.status == "success") {
                        _coinDetailLiveData.value = coinDetailResponse.result?.coin

                    } else {
                        print("status is not success")
                    }
                } catch (e: Exception) {
                    println("Handle Exception $e")
                }
            }
        }
        }

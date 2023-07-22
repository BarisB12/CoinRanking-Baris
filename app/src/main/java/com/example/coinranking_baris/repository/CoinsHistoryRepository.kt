package com.example.coinranking_baris.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.coinranking_baris.api.CoinService
import com.example.coinranking_baris.api.Service
import com.example.coinranking_baris.model.History
import com.example.coinranking_baris.model.HistoryX

class CoinsHistoryRepository {
    private var historyService: CoinService = Service.getCoinService()

    suspend fun getAllHistoryCoins():History{
        try {
            val historyList = this.historyService.getAllHistoryCoins()
            return historyList

        } catch (e: Exception) {
            Log.e("CoinsHistoryRepository", "getAllHistoryCoins: Error - ${e.message}", e)
            throw e
        }
    }
}
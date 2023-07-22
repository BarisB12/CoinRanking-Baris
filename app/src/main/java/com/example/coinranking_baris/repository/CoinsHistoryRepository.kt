package com.example.coinranking_baris.repository

import android.util.Log
import com.example.coinranking_baris.api.CoinsService
import com.example.coinranking_baris.api.Service
import com.example.coinranking_baris.model.History

class CoinsHistoryRepository {
    private var historyService: CoinsService = Service.getCoinsService()

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
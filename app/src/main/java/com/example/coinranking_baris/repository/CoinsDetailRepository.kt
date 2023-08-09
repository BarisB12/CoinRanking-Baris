package com.example.coinranking_baris.repository

import android.util.Log
import com.example.coinranking_baris.api.CoinsService
import com.example.coinranking_baris.api.Service
import com.example.coinranking_baris.model.Coins
import com.example.coinranking_baris.model.History

class CoinsDetailRepository {
    private var detailService: CoinsService = Service.getCoinsService()

    suspend fun getAllDetailCoins():Coins {
        try {
            val detailList = this.detailService.getAllDetailCoins()
            return detailList

        } catch (e: Exception) {
            Log.e("CoinsHistoryRepository", "getAllHistoryCoins: Error - ${e.message}", e)
            throw e
        }
    }
}
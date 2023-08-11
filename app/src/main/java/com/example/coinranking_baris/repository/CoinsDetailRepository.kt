package com.example.coinranking_baris.repository

import android.util.Log
import com.example.coinranking_baris.api.CoinsService
import com.example.coinranking_baris.api.Service
import com.example.coinranking_baris.model.CoinDetailResponse
import com.example.coinranking_baris.model.Coins

class CoinsDetailRepository {
    private var detailService: CoinsService = Service.getCoinsService()

    suspend fun getCoinDetail(uuid: String): CoinDetailResponse {
        try {
            return detailService.getCoinDetail(uuid)

        } catch (e: Exception) {
            Log.e("CoinsHistoryRepository", "getAllHistoryCoins: Error - ${e.message}", e)
            throw e
        }
    }
}
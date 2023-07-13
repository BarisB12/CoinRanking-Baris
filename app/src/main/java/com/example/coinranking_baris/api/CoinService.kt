package com.example.coinranking_baris.api

import com.example.coinranking_baris.model.Coins
import com.example.coinranking_baris.model.Data
import retrofit2.http.GET
import retrofit2.http.Header

interface CoinService {
    @GET("v2/coins")
    suspend fun getAllCoins(): Coins
}

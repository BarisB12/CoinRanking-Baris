package com.example.coinranking_baris.api

import com.example.coinranking_baris.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinsService {
    @GET("v2/coins")
    suspend fun getAllCoins(): Coins

    @GET("v2/coins")
    suspend fun getAllCoinsWithPage(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Coins

    @GET("v2/coin/Qwsogvtv82FCd/history")
    suspend fun getAllHistoryCoins(): History
}

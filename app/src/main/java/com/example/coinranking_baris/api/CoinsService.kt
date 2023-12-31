package com.example.coinranking_baris.api

import android.telecom.Call
import com.example.coinranking_baris.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinsService {
    @GET("v2/coins")
    suspend fun getAllCoins(): Coins

    @GET("v2/coins")
    suspend fun getCoins(
        @Query("orderBy") sort: String, // can be one of "price" "marketCap" "24hVolume" "change" "listedAt",
        @Query("orderDirection") orderDirection: String // can be one of "desc" "asc"
    ): Coins

    @GET("v2/coin/Qwsogvtv82FCd/history")
    suspend fun getAllHistoryCoins(): History

    @GET("v2/coin/:uuid")
    suspend fun getAllDetailCoins(): Coins

    @GET("v2/coin/{uuid}")
    suspend fun getCoinDetail(@Path("uuid") uuid: String): CoinDetailResponse

}

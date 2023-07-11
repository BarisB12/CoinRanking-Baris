package com.example.coinranking_baris.model


import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("total")
    val total: Int,
    @SerializedName("total24hVolume")
    val total24hVolume: String,
    @SerializedName("totalCoins")
    val totalCoins: Int,
    @SerializedName("totalExchanges")
    val totalExchanges: Int,
    @SerializedName("totalMarketCap")
    val totalMarketCap: String,
    @SerializedName("totalMarkets")
    val totalMarkets: Int
)
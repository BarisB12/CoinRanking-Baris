package com.example.coinranking_baris.model


import com.google.gson.annotations.SerializedName

data class HistoryX(
    @SerializedName("price")
    val price: String,
    @SerializedName("timestamp")
    val timestamp: Int
)

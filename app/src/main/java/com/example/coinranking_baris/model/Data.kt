package com.example.coinranking_baris.model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("coins")
    val coins: List<Coin>,
    @SerializedName("stats")
    val stats: Stats
)
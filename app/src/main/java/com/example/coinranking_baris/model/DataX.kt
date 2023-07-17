package com.example.coinranking_baris.model


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("change")
    val change: String,
    @SerializedName("history")
    val history: List<HistoryX>
)
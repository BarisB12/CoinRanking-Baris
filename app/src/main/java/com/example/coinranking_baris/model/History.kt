package com.example.coinranking_baris.model


import com.google.gson.annotations.SerializedName

data class History(
    @SerializedName("data")
    val data: DataX,
    @SerializedName("status")
    val status: String
)
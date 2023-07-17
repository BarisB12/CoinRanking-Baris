package com.example.coinranking_baris.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coin(
    @SerializedName("btcPrice")
    val btcPrice: String,
    @SerializedName("change")
    val change: String,
    @SerializedName("coinrankingUrl")
    val coinrankingUrl: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("24hVolume")
    val hVolume: String,
    @SerializedName("iconUrl")
    val iconUrl: String,
    @SerializedName("listedAt")
    val listedAt: Int,
    @SerializedName("lowVolume")
    val lowVolume: Boolean,
    @SerializedName("marketCap")
    val marketCap: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("sparkline")
    val sparkline: List<String>,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("tier")
    val tier: Int,
    @SerializedName("uuid")
    val uuid: String
) : Serializable
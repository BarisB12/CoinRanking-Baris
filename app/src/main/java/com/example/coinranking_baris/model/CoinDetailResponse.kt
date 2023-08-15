package com.example.coinranking_baris.model


import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(
    @SerializedName("data")
    val result: Data?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("coin")
        val coin: CoinDetail?
    ) {
        data class CoinDetail(
            var isFav: Boolean = false,
            @SerializedName("allTimeHigh")
            val allTimeHigh: AllTimeHigh?,
            @SerializedName("btcPrice")
            val btcPrice: String?,
            @SerializedName("change")
            val change: String?,
            @SerializedName("coinrankingUrl")
            val coinrankingUrl: String?,
            @SerializedName("color")
            val color: String?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("fullyDilutedMarketCap")
            val fullyDilutedMarketCap: String?,
            @SerializedName("24hVolume")
            val hVolume: String?,
            @SerializedName("iconUrl")
            val iconUrl: String?,
            @SerializedName("links")
            val links: List<Link?>?,
            @SerializedName("listedAt")
            val listedAt: Int?,
            @SerializedName("lowVolume")
            val lowVolume: Boolean?,
            @SerializedName("marketCap")
            val marketCap: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("notices")
            val notices: List<Notice?>?,
            @SerializedName("numberOfExchanges")
            val numberOfExchanges: Int?,
            @SerializedName("numberOfMarkets")
            val numberOfMarkets: Int?,
            @SerializedName("price")
            val price: String?,
            @SerializedName("priceAt")
            val priceAt: Int?,
            @SerializedName("rank")
            val rank: Int?,
            @SerializedName("sparkline")
            val sparkline: List<String?>?,
            @SerializedName("supply")
            val supply: Supply?,
            @SerializedName("symbol")
            val symbol: String?,
            @SerializedName("tags")
            val tags: List<String?>?,
            @SerializedName("uuid")
            val uuid: String?,
            @SerializedName("websiteUrl")
            val websiteUrl: String?
        ) {
            data class AllTimeHigh(
                @SerializedName("price")
                val price: String?,
                @SerializedName("timestamp")
                val timestamp: Int?
            )

            data class Link(
                @SerializedName("name")
                val name: String?,
                @SerializedName("type")
                val type: String?,
                @SerializedName("url")
                val url: String?
            )

            data class Notice(
                @SerializedName("type")
                val type: String?,
                @SerializedName("value")
                val value: String?
            )

            data class Supply(
                @SerializedName("circulating")
                val circulating: String?,
                @SerializedName("confirmed")
                val confirmed: Boolean?,
                @SerializedName("max")
                val max: String?,
                @SerializedName("supplyAt")
                val supplyAt: Int?,
                @SerializedName("total")
                val total: String?
            )
        }
    }
}
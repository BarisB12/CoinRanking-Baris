package com.example.coinranking_baris.api

class Service {

    companion object {
        private const val BASE_URL = "https://api.coinranking.com/"

        fun getCoinsService(): CoinsService {
            return CoinClient.getClient(BASE_URL).create(CoinsService::class.java)
        }
    }
}
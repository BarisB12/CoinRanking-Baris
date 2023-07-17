package com.example.coinranking_baris.repository

import com.example.coinranking_baris.api.CoinService
import com.example.coinranking_baris.api.Service
import com.example.coinranking_baris.model.Coins


class CoinRepository(
    private var service: CoinService = Service.getCoinService()
){
        suspend fun getAllCoins(): Coins {
            return this.service.getAllCoins()
        }
}
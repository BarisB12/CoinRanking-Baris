package com.example.coinranking_baris.repository

import com.example.coinranking_baris.api.CoinsService
import com.example.coinranking_baris.api.Service
import com.example.coinranking_baris.model.Coins


class CoinsRepository(
    private var service: CoinsService = Service.getCoinsService()
){
        suspend fun getAllCoins(): Coins {
            return this.service.getAllCoins()
        }
}
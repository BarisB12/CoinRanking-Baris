package com.example.coinranking_baris.repository

import com.example.coinranking_baris.api.CoinService
import com.example.coinranking_baris.api.Service
import com.example.coinranking_baris.model.Data

class CoinRepository(
    private var service: CoinService = Service.getCoinService()
){
        suspend fun getAllDoctors(): Data {
            return this.service.getAllcoins()
        }
}
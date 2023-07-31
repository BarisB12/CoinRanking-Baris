package com.example.coinranking_baris.repository

import com.example.coinranking_baris.api.CoinsService
import com.example.coinranking_baris.api.Service
import com.example.coinranking_baris.model.Coins


class CoinsRepository(
    private var service: CoinsService = Service.getCoinsService()
) {
    suspend fun getAllCoins(): Coins {
        return this.service.getAllCoins()
    }

    suspend fun getCoinsWithSortOption(
        sortOption: String? = null,
        sortDirection: String? = null
    ): Coins {
        return safeLet(sortOption, sortDirection) { i1, i2 ->
            this.service.getCoins(i1, i2)
        } ?: this.service.getAllCoins()
    }

    inline fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
        return if (p1 != null && p2 != null) block(p1, p2) else null
    }
}
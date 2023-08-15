package com.example.coinranking_baris.repository

import com.example.coinranking_baris.sharedprefs.SharedPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesRepository {
    suspend fun checkFavoritedCoin(coinName: String): Boolean {
        return withContext(Dispatchers.Default) {
            SharedPrefs.checkFavoritedCoin(coinName)
        }
    }
}
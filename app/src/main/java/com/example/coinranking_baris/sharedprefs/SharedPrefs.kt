package com.example.coinranking_baris.sharedprefs

import android.content.Context
import android.content.SharedPreferences

object SharedPrefs {
    private const val APP_PREFERENCES = "app_preferences"
    lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun nightColorMode() {
        val editor = preferences.edit()
        editor.putBoolean("night", true)
        editor.apply()
    }

    fun dayColorMode() {
        val editor = preferences.edit()
        editor.putBoolean("night", false)
        editor.apply()
    }
    fun getNightMode(): Boolean {
        return preferences.getBoolean("night", false)
    }
    fun addFavoritedCoin(coinName: String) {
        val favoritedCoins = getFavoritedCoins().toMutableSet()
        favoritedCoins.add(coinName)
        preferences.edit().putStringSet("favoritedCoins", favoritedCoins).apply()
    }

    fun removeFavoritedCoin(coinName: String) {
        val favoritedCoins = getFavoritedCoins().toMutableSet()
        favoritedCoins.remove(coinName)
        preferences.edit().putStringSet("favoritedCoins", favoritedCoins).apply()
    }

    fun checkFavoritedCoin(coinName: String): Boolean {
        return getFavoritedCoins().contains(coinName)
    }

    fun getFavoritedCoins(): Set<String> {
        return preferences.getStringSet("favoritedCoins", emptySet()) ?: emptySet()
    }
}
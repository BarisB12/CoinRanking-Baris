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
}
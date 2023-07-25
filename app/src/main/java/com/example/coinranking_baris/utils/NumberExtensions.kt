package com.example.coinranking_baris.utils

fun Double?.orZero() : Double {
    return this ?: 0.0
}
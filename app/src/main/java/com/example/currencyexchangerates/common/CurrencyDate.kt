package com.example.currencyexchangerates.common

data class CurrencyDate(
    val day: Int,
    val month: Int,
    val year: Int
) {
    override fun toString(): String {
        return "${if (day >= 10) day else "0$day"}.${if (month >= 10) month else "0$month"}.$year"
    }

    fun toNetworkString(): String {
        return "$year-${if (month >= 10) month else "0$month"}-${if (day >= 10) day else "0$day"}"
    }
}

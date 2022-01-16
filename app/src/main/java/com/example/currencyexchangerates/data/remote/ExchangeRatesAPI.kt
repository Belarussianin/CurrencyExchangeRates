package com.example.currencyexchangerates.data.remote

import com.example.currencyexchangerates.data.remote.dto.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesAPI {
    @GET("rates?")
    suspend fun getExactRates(@Query("ondate") date: String, @Query("periodicity") periodicity: Int = 0): List<CurrencyDto>
}
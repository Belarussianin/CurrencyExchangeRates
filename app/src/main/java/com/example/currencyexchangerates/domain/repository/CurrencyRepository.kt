package com.example.currencyexchangerates.domain.repository

import com.example.currencyexchangerates.common.CurrencyDate
import com.example.currencyexchangerates.data.remote.dto.CurrencyDto
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    suspend fun getCurrencyRates(date: CurrencyDate): List<CurrencyDto>
    suspend fun addCurrencyInCache(currencyDbEntity: CurrencyDbEntity)
    suspend fun getCurrencyFromCache(id: Int): CurrencyDbEntity?
    suspend fun saveCurrencyInCache(currencyDbEntity: CurrencyDbEntity)
    suspend fun getAllCurrenciesFromCache(): Flow<List<CurrencyDbEntity>>
}
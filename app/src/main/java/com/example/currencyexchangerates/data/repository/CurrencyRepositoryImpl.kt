package com.example.currencyexchangerates.data.repository

import com.example.currencyexchangerates.common.CurrencyDate
import com.example.currencyexchangerates.data.database.CurrencyDao
import com.example.currencyexchangerates.data.remote.ExchangeRatesAPI
import com.example.currencyexchangerates.data.remote.dto.CurrencyDto
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import com.example.currencyexchangerates.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class CurrencyRepositoryImpl(
    private val currencyApi: ExchangeRatesAPI,
    private val currencyDao: CurrencyDao
) : CurrencyRepository {
    override suspend fun getCurrencyRates(date: CurrencyDate): List<CurrencyDto> {
        return currencyApi.getExactRates(date.toNetworkString())
    }

    override suspend fun addCurrencyInCache(currencyDbEntity: CurrencyDbEntity) {
        return currencyDao.insertIfNotInDb(currencyDbEntity)
    }

    override suspend fun saveCurrencyInCache(currencyDbEntity: CurrencyDbEntity) {
        return currencyDao.insert(currencyDbEntity)
    }

    override suspend fun getCurrencyFromCache(id: Int): CurrencyDbEntity? {
        return currencyDao.getById(id)
    }

    override suspend fun getAllCurrenciesFromCache(): Flow<List<CurrencyDbEntity>> {
        return currencyDao.getAll()
    }
}
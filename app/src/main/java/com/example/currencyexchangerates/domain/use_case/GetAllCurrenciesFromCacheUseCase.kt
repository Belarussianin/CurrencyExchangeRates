package com.example.currencyexchangerates.domain.use_case

import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import com.example.currencyexchangerates.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow

class GetAllCurrenciesFromCacheUseCase(private val repository: CurrencyRepository) {
    suspend fun execute(): Flow<List<CurrencyDbEntity>> {
        return repository.getAllCurrenciesFromCache()
    }
}
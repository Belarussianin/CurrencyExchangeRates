package com.example.currencyexchangerates.domain.use_case

import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import com.example.currencyexchangerates.domain.repository.CurrencyRepository

class AddCurrencyInCacheUseCase(private val repository: CurrencyRepository) {
    suspend fun execute(currencyDbEntity: CurrencyDbEntity) {
        return repository.addCurrencyInCache(currencyDbEntity)
    }
}
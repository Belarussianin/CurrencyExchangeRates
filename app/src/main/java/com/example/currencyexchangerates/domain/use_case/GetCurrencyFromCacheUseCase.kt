package com.example.currencyexchangerates.domain.use_case

import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import com.example.currencyexchangerates.domain.repository.CurrencyRepository

class GetCurrencyFromCacheUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(id: Int): CurrencyDbEntity? {
        return repository.getCurrencyFromCache(id)
    }
}
package com.example.currencyexchangerates.domain.use_case

import com.example.currencyexchangerates.common.CurrencyDate
import com.example.currencyexchangerates.data.remote.dto.CurrencyDto
import com.example.currencyexchangerates.domain.repository.CurrencyRepository

class GetCurrencyRatesUseCase(private val repository: CurrencyRepository) :
    BaseStateUseCase<List<CurrencyDto>, CurrencyDate>() {

    override suspend fun run(params: CurrencyDate): List<CurrencyDto> {
        return repository.getCurrencyRates(params)
    }
}
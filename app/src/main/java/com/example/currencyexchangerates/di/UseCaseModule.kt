package com.example.currencyexchangerates.di

import com.example.currencyexchangerates.data.repository.CurrencyRepositoryImpl
import com.example.currencyexchangerates.domain.repository.CurrencyRepository
import com.example.currencyexchangerates.domain.use_case.AddCurrencyInCacheUseCase
import com.example.currencyexchangerates.domain.use_case.GetAllCurrenciesFromCacheUseCase
import com.example.currencyexchangerates.domain.use_case.GetCurrencyFromCacheUseCase
import com.example.currencyexchangerates.domain.use_case.GetCurrencyRatesUseCase
import com.example.currencyexchangerates.domain.use_case.SaveCurrencyInCacheUseCase

import org.koin.dsl.module

val useCaseModule = module {
    factory { AddCurrencyInCacheUseCase(get<CurrencyRepositoryImpl>()) }
    factory { SaveCurrencyInCacheUseCase(get<CurrencyRepositoryImpl>()) }
    factory { GetCurrencyFromCacheUseCase(get<CurrencyRepositoryImpl>()) }
    factory { GetAllCurrenciesFromCacheUseCase(get<CurrencyRepositoryImpl>()) }
    factory { GetCurrencyRatesUseCase(get<CurrencyRepositoryImpl>()) }
}
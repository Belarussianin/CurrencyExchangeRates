package com.example.currencyexchangerates.di

import com.example.currencyexchangerates.data.database.CurrencyDatabase
import com.example.currencyexchangerates.data.repository.CurrencyRepositoryImpl
import com.example.currencyexchangerates.domain.repository.CurrencyRepository
import org.koin.dsl.module

val appModule = module {
    // Database
    single { CurrencyDatabase.getDatabase(get()) }
    // Repository
    single { CurrencyRepositoryImpl(get(), get<CurrencyDatabase>().currencyDao()) }
}
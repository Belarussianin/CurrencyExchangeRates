package com.example.currencyexchangerates.di

import com.example.currencyexchangerates.MainViewModel
import com.example.currencyexchangerates.presentation.ui.screen.rates.settings.ExchangeRatesSettingsScreen
import com.example.currencyexchangerates.presentation.ui.screen.rates.settings.ExchangeRatesSettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get(), get(), get(), get()) }
    viewModel { ExchangeRatesSettingsViewModel(get(), get()) }
}
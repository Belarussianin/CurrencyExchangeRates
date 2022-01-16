package com.example.currencyexchangerates.di

import com.example.currencyexchangerates.MainViewModel
import com.example.currencyexchangerates.presentation.ui.screen.rates.settings.ExchangeRatesSettingsScreen
import com.example.currencyexchangerates.presentation.ui.screen.rates.settings.ExchangeRatesSettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { params -> MainViewModel(get(), get(), get(), get(), params.get()) }
    viewModel { params -> ExchangeRatesSettingsViewModel(get(), get(), params.get()) }
}
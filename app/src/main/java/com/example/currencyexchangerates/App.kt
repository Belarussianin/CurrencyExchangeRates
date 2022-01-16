package com.example.currencyexchangerates

import android.app.Application
import com.example.currencyexchangerates.di.appModule
import com.example.currencyexchangerates.di.networkModule
import com.example.currencyexchangerates.di.useCaseModule
import com.example.currencyexchangerates.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(appModule, networkModule, useCaseModule, viewModelModule))
        }
    }
}
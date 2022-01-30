package com.example.currencyexchangerates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.currencyexchangerates.presentation.ui.screen.NavGraphs
import com.example.currencyexchangerates.ui.theme.CurrencyExchangeRatesTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            CurrencyExchangeRatesTheme {
                DestinationsNavHost(NavGraphs.root)
            }
        }
    }
}
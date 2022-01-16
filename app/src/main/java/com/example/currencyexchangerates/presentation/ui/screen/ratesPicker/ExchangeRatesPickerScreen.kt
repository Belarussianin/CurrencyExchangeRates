package com.example.currencyexchangerates.presentation.ui.screen.ratesPicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.currencyexchangerates.presentation.ui.screen.destinations.ExchangeRatesScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun ExchangeRatesPickerScreen(
    navigator: DestinationsNavigator
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.clickable {
                navigator.navigate(ExchangeRatesScreenDestination.route) //navController.navigate(com.example.currencyexchangerates.presentation.navigation.Navigation.AppScreen.Rates.route)
            },
            textAlign = TextAlign.Center,
            text = "To Rates"
        )
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun DefaultPreview() {
//    CurrencyExchangeRatesTheme {
//        ExchangeRatesPickerScreen(rememberNavController())
//    }
//}
package com.example.currencyexchangerates.presentation.ui.screen.rates.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.currencyexchangerates.MainViewModel
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.common.MenuAction
import com.example.currencyexchangerates.presentation.components.CurrencySettingCard
import com.example.currencyexchangerates.presentation.components.MainTopAppBar
import com.example.currencyexchangerates.presentation.ui.screen.destinations.ExchangeRatesScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel

@Composable
private fun RatesSettingsTopAppBar(
    navigator: DestinationsNavigator,
    onSave: () -> Unit
) {
    MainTopAppBar(
        title = stringResource(id = R.string.rates_settings),
        navigationIcon = MenuAction.Back,
        onNavigateClick = {
            navigator.navigate(ExchangeRatesScreenDestination.route)
        },
        actions = listOf(
            MenuAction.Complete
        ),
        onActionClick = {
            when (it) {
                MenuAction.Complete.label -> {
                    onSave()
                    navigator.navigate(ExchangeRatesScreenDestination.route)
                }
            }
        }
    )
}

@Destination
@Composable
fun ExchangeRatesSettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: ExchangeRatesSettingsViewModel = getViewModel()
) {
    val save = remember { mutableStateOf(false) }
    Scaffold(
        topBar = { RatesSettingsTopAppBar(navigator) { save.value = true } }
    ) {
        val currencyListState =
            viewModel.currencySettingsListState.collectAsState()
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(currencyListState.value.data?.size ?: 0) { index ->
                currencyListState.value.data?.let { list ->
                    CurrencySettingCard(
                        list[index].copy(position = index),
                        { viewModel.saveCurrencySetting(it.copy(position = index)) },
                        isChanged = save.value,
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun ExchangeRatesSettingsScreenPreview() {
//    CurrencyExchangeRatesTheme {
//        ExchangeRatesSettingsScreen(
//            rememberNavController()
//        )
//    }
//}
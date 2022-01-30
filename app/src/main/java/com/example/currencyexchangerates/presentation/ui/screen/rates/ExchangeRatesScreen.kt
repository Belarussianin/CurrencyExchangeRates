package com.example.currencyexchangerates.presentation.ui.screen.rates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.currencyexchangerates.MainViewModel
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.common.MenuAction
import com.example.currencyexchangerates.presentation.components.CurrencyCard
import com.example.currencyexchangerates.presentation.components.MainTopAppBar
import com.example.currencyexchangerates.presentation.ui.screen.destinations.ExchangeRatesPickerScreenDestination
import com.example.currencyexchangerates.presentation.ui.screen.destinations.ExchangeRatesSettingsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Composable
private fun RatesTopAppBar(
    navigator: DestinationsNavigator
) {
    MainTopAppBar(
        title = stringResource(id = R.string.app_name),
        navigationIcon = MenuAction.Back,
        onNavigateClick = {
            navigator.navigate(ExchangeRatesPickerScreenDestination.route)
        },
        actions = listOf(
            MenuAction.Settings
        ),
        onActionClick = {
            when (it) {
                MenuAction.Settings.label -> {
                    navigator.navigate(ExchangeRatesSettingsScreenDestination.route)
                }
            }
        }
    )
}

@Destination
@Composable
fun ExchangeRatesScreen(
    navigator: DestinationsNavigator,
    viewModel: MainViewModel = getViewModel()
) {
    Scaffold(
        topBar = { RatesTopAppBar(navigator) }
    ) {
        val yesterdayDateState =
            viewModel.yesterdayDateState.collectAsState()
        val todayDateState = viewModel.todayDateState.collectAsState()

        val currencyListState = viewModel.outputCurrencyList.collectAsState()

        Column {
            TopAppBar {
                Box(modifier = Modifier.weight(1.7f))
                Row(
                    modifier = Modifier.weight(1.3f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = yesterdayDateState.value, maxLines = 1)
                    Text(text = todayDateState.value, maxLines = 1)
                }
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(currencyListState.value.data ?: listOf()) { item ->
                    key(item) {
                        CurrencyCard(
                            item.first,
                            extraOfficialRate = item.second,
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun DefaultPreview() {
//    CurrencyExchangeRatesTheme {
//        ExchangeRatesScreen()
//    }
//}
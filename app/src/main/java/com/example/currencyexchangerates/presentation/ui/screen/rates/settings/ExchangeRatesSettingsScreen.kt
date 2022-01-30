package com.example.currencyexchangerates.presentation.ui.screen.rates.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.common.MenuAction
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import com.example.currencyexchangerates.presentation.components.CurrencySettingCard
import com.example.currencyexchangerates.presentation.components.MainTopAppBar
import com.example.currencyexchangerates.presentation.ui.screen.destinations.ExchangeRatesScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.burnoutcrew.reorderable.ReorderableState
import org.burnoutcrew.reorderable.draggedItem
import org.burnoutcrew.reorderable.move
import org.burnoutcrew.reorderable.rememberReorderState
import org.burnoutcrew.reorderable.reorderable
import org.koin.androidx.compose.getViewModel

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

@OptIn(ExperimentalComposeUiApi::class)
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

        val columnState = rememberReorderState()

        currencyListState.value.data?.let {
            CurrencyListColumn(
                state = columnState,
                data = it,
                save.value
            ) {
                viewModel.saveCurrencySetting(it)
            }
        }
    }
}

@Composable
fun CurrencyListColumn(
    state: ReorderableState = rememberReorderState(),
    data: List<CurrencyDbEntity>,
    save: Boolean = false,
    saveCurrencySetting: (CurrencyDbEntity) -> Unit
) {
    val list = remember { data.toMutableStateList() }
    if (save) {
        list.forEach {
            saveCurrencySetting(it)
        }
    }

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .reorderable(state, { from, to ->
                list[from.index] = list[from.index].copy(position = to.index)
                list[to.index] = list[to.index].copy(position = from.index)
                list.move(from.index, to.index)
            })
            .fillMaxWidth()
    ) {
        itemsIndexed(list, { _, it -> it.id }) { index, item ->
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .draggedItem(state.offsetByKey(item.id))
            ) {
                val isActiveState = rememberSaveable(item.isActive) {
                    mutableStateOf(item.isActive)
                }

                key(item) {
                    CurrencySettingCard(
                        currencyDbEntity = item.copy(isActive = isActiveState.value),
                        onChange = { changed ->
                            isActiveState.value = changed.isActive
                            list[index] = changed
                        },
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        reorderState = state
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
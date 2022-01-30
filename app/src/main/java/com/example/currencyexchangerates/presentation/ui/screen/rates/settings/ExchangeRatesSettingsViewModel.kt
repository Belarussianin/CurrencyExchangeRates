package com.example.currencyexchangerates.presentation.ui.screen.rates.settings

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangerates.common.UiState
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import com.example.currencyexchangerates.domain.use_case.GetAllCurrenciesFromCacheUseCase
import com.example.currencyexchangerates.domain.use_case.SaveCurrencyInCacheUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ExchangeRatesSettingsViewModel(
    private val saveCurrencyInCacheUseCase: SaveCurrencyInCacheUseCase,
    private val getAllCurrenciesFromCacheUseCase: GetAllCurrenciesFromCacheUseCase
) : ViewModel() {

    private val _currencySettingsListState =
        MutableStateFlow<UiState<List<CurrencyDbEntity>>>(UiState())
    val currencySettingsListState: StateFlow<UiState<List<CurrencyDbEntity>>> get() = _currencySettingsListState

    init {
        fetchDataFromCache()
    }

    private fun fetchDataFromCache() {
        viewModelScope.launch {
            getAllCurrenciesFromCacheUseCase.execute()
                .onEach {
                    _currencySettingsListState.value = UiState(
                        isLoading = false,
                        data = it.sortedBy { it.position }
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    fun saveCurrencySetting(currencyDbEntity: CurrencyDbEntity) {
        viewModelScope.launch {
            saveCurrencyInCacheUseCase.execute(currencyDbEntity)
        }
    }
}
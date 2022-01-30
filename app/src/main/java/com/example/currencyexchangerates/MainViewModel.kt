package com.example.currencyexchangerates

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangerates.common.DataState
import com.example.currencyexchangerates.common.UiState
import com.example.currencyexchangerates.common.toCurrencyDbEntity
import com.example.currencyexchangerates.common.today
import com.example.currencyexchangerates.common.yesterday
import com.example.currencyexchangerates.data.remote.dto.CurrencyDto
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import com.example.currencyexchangerates.domain.use_case.AddCurrencyInCacheUseCase
import com.example.currencyexchangerates.domain.use_case.GetAllCurrenciesFromCacheUseCase
import com.example.currencyexchangerates.domain.use_case.GetCurrencyFromCacheUseCase
import com.example.currencyexchangerates.domain.use_case.GetCurrencyRatesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.GregorianCalendar
import kotlin.math.min

class MainViewModel(
    private val getYesterdayCurrencyUseCase: GetCurrencyRatesUseCase,
    private val getTodayCurrencyUseCase: GetCurrencyRatesUseCase,
    private val getCurrencyFromCacheUseCase: GetCurrencyFromCacheUseCase,
    private val getCurrencySettings: GetAllCurrenciesFromCacheUseCase,
    private val addCurrencyInCacheUseCase: AddCurrencyInCacheUseCase
) : ViewModel() {

    private val _currencySettingsList = MutableStateFlow<UiState<List<CurrencyDbEntity>>>(UiState())

    private val _yesterdayCurrencyListState =
        MutableStateFlow<UiState<List<CurrencyDto>>>(UiState())
    private val _todayCurrencyListState = MutableStateFlow<UiState<List<CurrencyDto>>>(UiState())

    private val _yesterdayDateState = MutableStateFlow("")
    val yesterdayDateState: StateFlow<String> get() = _yesterdayDateState

    private val _todayDateState = MutableStateFlow("")
    val todayDateState: StateFlow<String> get() = _todayDateState

    private val _currencyList: Flow<List<Pair<CurrencyDto, Double>>> =
        combine(_yesterdayCurrencyListState, _todayCurrencyListState) { yesterday, today ->
            val resultList = mutableListOf<Pair<CurrencyDto, Double>>()
            yesterday.data?.let { yesterdayList ->
                today.data?.let { todayList ->
                    for (index in 0 until min(yesterdayList.size, todayList.size)) {
                        resultList.add(
                            Pair(
                                yesterdayList[index],
                                todayList[index].Cur_OfficialRate
                            )
                        )
                    }
                }
            }
            resultList
        }

    private val _resultList =
        combine(_currencyList, _currencySettingsList) { currencyList, settingCurrency ->
            val resultList = mutableListOf<Pair<CurrencyDbEntity, Double>>()
            currencyList.forEachIndexed { _, pair ->
                settingCurrency.data?.forEach { currencySetting ->
                    if (currencySetting.id == pair.first.Cur_ID && currencySetting.isActive) {
                        resultList.add(
                            Pair(
                                pair.first.toCurrencyDbEntity(position = currencySetting.position),
                                pair.second
                            )
                        )
                    }
                }
            }
            resultList.sortedBy { it.first.position }.toList()
        }

    private val _outputCurrencyList =
        MutableStateFlow<UiState<List<Pair<CurrencyDbEntity, Double>>>>(UiState())
    val outputCurrencyList: StateFlow<UiState<List<Pair<CurrencyDbEntity, Double>>>> get() = _outputCurrencyList

    init {
        getSettingList()
        getCurrencyList()
        getOutputList()
    }

    private fun saveDataToCache(
        currencyDto: CurrencyDto,
        position: Int? = null
    ) {
        viewModelScope.launch {
            val currencyFromCache = getCurrencyFromCacheUseCase.execute(currencyDto.Cur_ID)

            addCurrencyInCacheUseCase.execute(
                currencyDto.toCurrencyDbEntity(
                    currencyFromCache?.isActive ?: true,
                    currencyFromCache?.position ?: position
                )
            )
        }
    }

    private fun getSettingList() {
        viewModelScope.launch {
            getCurrencySettings.execute().onEach {
                _currencySettingsList.value = UiState(isLoading = false, data = it)
            }.launchIn(viewModelScope)
        }
    }

    private fun getCurrencyList() {
        val calendar = GregorianCalendar.getInstance()

        val yesterday = calendar.yesterday()
        getYesterdayCurrencyUseCase.execute(yesterday).onEach { dataState ->
            when (dataState) {
                is DataState.Ready -> {
                    dataState.data.let { list ->
                        list.forEachIndexed { index, currency ->
                            saveDataToCache(currency, index)
                        }
                    }
                    _yesterdayCurrencyListState.value = UiState(false, dataState.data)
                }
                is DataState.Error -> {

                }
                is DataState.Loading -> {
                    _yesterdayCurrencyListState.value = UiState(true)
                }
            }
        }.launchIn(viewModelScope)
        _yesterdayDateState.value = with(yesterday.toString()) {
            trim().removeRange(lastIndex - 3..lastIndex - 2)
        }

        val today = calendar.today()
        getTodayCurrencyUseCase.execute(today).onEach { dataState ->
            when(dataState) {
                is DataState.Ready -> {
                    _todayCurrencyListState.value = UiState(false, dataState.data)
                }
                is DataState.Error -> {

                }
                is DataState.Loading -> {
                    _todayCurrencyListState.value = UiState(true)
                }
            }
        }.launchIn(viewModelScope)
        _todayDateState.value = with(today.toString()) {
            trim().removeRange(lastIndex - 3..lastIndex - 2)
        }
    }

    private fun getOutputList() {
        _resultList.onEach {
            _outputCurrencyList.value = UiState(isLoading = false, data = it)
        }.launchIn(viewModelScope)
    }
}
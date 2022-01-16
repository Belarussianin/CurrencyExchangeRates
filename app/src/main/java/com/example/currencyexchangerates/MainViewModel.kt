package com.example.currencyexchangerates

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchangerates.common.UiState
import com.example.currencyexchangerates.common.toCurrencyDbEntity
import com.example.currencyexchangerates.common.today
import com.example.currencyexchangerates.common.yesterday
import com.example.currencyexchangerates.data.remote.dto.CurrencyDto
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import com.example.currencyexchangerates.domain.use_case.AddCurrencyInCacheUseCase
import com.example.currencyexchangerates.domain.use_case.GetCurrencyFromCacheUseCase
import com.example.currencyexchangerates.domain.use_case.GetCurrencyRatesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.GregorianCalendar

class MainViewModel(
    private val getYesterdayCurrencyUseCase: GetCurrencyRatesUseCase,
    private val getTodayCurrencyUseCase: GetCurrencyRatesUseCase,
    private val getCurrencyFromCacheUseCase: GetCurrencyFromCacheUseCase,
    private val addCurrencyInCacheUseCase: AddCurrencyInCacheUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _yesterdayCurrencyListState =
        MutableStateFlow<UiState<List<CurrencyDto>>>(UiState())
    val yesterdayCurrencyDtoListState: StateFlow<UiState<List<CurrencyDto>>> get() = _yesterdayCurrencyListState

    private val _todayCurrencyListState = MutableStateFlow<UiState<List<CurrencyDto>>>(UiState())
    val todayCurrencyDtoListState: StateFlow<UiState<List<CurrencyDto>>> get() = _todayCurrencyListState

    private val _yesterdayDateState = MutableStateFlow("")
    val yesterdayDateState: StateFlow<String> get() = _yesterdayDateState

    private val _todayDateState = MutableStateFlow("")
    val todayDateState: StateFlow<String> get() = _todayDateState

    init {
        getCurrencyList()
    }

    private fun saveDataToCache(
        currencyDto: CurrencyDto,
        isActive: Boolean = true,
        position: Int? = null
    ) {
        viewModelScope.launch {
            addCurrencyInCacheUseCase.execute(currencyDto.toCurrencyDbEntity(isActive, position))
        }
    }

    private fun getCurrencyList() {
        val calendar = GregorianCalendar.getInstance()
        val yesterday = calendar.yesterday()
        getYesterdayCurrencyUseCase.execute(yesterday)
            .onEach { dataState ->
                dataState.data?.let { list ->
                    for (index in list.indices) {
                        saveDataToCache(
                            list[index],
                            getCurrencyFromCacheUseCase(list[index].Cur_ID)?.isActive ?: true,
                            index
                        )
                    }
                }
                _yesterdayCurrencyListState.value = UiState(false, dataState.data)
                _yesterdayDateState.value = with(yesterday.toString()) {
                    trim().removeRange(lastIndex - 3..lastIndex - 2)
                }
            }
            .launchIn(viewModelScope)

        val today = calendar.today()
        getTodayCurrencyUseCase.execute(today).onEach {
            _todayCurrencyListState.value = UiState(false, it.data)
            _todayDateState.value = with(today.toString()) {
                trim().removeRange(lastIndex - 3..lastIndex - 2)
            }
        }.launchIn(viewModelScope)
    }
}
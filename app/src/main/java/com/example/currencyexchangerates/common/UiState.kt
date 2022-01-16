package com.example.currencyexchangerates.common

data class UiState<T>(
    val isLoading: Boolean = true,
    val data: T? = null,
    val error: String = ""
)
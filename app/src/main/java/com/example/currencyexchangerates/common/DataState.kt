package com.example.currencyexchangerates.common

sealed class DataState<T>(open val data: T? = null, val message: String? = null) where T : Any? {
    class Ready<T>(override val data: T) : DataState<T>(data)
    class Error<T>(message: String) : DataState<T>(message = message)
    class Loading<T> : DataState<T>()
}
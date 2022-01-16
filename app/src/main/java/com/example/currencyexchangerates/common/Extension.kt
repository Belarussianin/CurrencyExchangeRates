package com.example.currencyexchangerates.common

import com.example.currencyexchangerates.data.remote.dto.CurrencyDto
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import java.util.Calendar

fun Calendar.yesterday(): CurrencyDate {
    add(Calendar.DATE, -1)
    val date = CurrencyDate(
        get(Calendar.DAY_OF_MONTH),
        get(Calendar.MONTH) + 1,
        get(Calendar.YEAR)
    )
    add(Calendar.DATE, +1)
    return date
}

fun Calendar.today(): CurrencyDate {
    return CurrencyDate(
        get(Calendar.DAY_OF_MONTH),
        get(Calendar.MONTH) + 1,
        get(Calendar.YEAR)
    )
}

fun CurrencyDto.toCurrencyDbEntity(isActive: Boolean = true, position: Int? = null) = CurrencyDbEntity(
    Cur_Abbreviation,
    Cur_ID,
    Cur_Name,
    Cur_OfficialRate,
    Cur_Scale,
    isActive,
    position
)
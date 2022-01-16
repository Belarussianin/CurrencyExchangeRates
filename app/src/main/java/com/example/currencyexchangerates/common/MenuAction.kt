package com.example.currencyexchangerates.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.currencyexchangerates.R

sealed class MenuAction(
    @DrawableRes val icon: Int,
    @StringRes val label: Int
) {
    object Menu : MenuAction(R.drawable.ic_menu_24, R.string.menu)
    object Settings : MenuAction(R.drawable.ic_settings_24, R.string.settings)
    object Back : MenuAction(R.drawable.ic_back_24, R.string.back)
    object Complete : MenuAction(R.drawable.ic_complete_24, R.string.complete)
}
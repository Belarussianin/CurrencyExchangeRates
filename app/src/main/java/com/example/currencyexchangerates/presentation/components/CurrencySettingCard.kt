package com.example.currencyexchangerates.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.currencyexchangerates.common.MenuAction
import com.example.currencyexchangerates.data.remote.dto.CurrencyDto
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity

@Composable
fun CurrencySettingCard(
    currencyDbEntity: CurrencyDbEntity,
    onChange: (CurrencyDbEntity) -> Unit,
    isChanged: Boolean,
    modifier: Modifier = Modifier
) {
    val isActive = remember { mutableStateOf(currencyDbEntity.isActive) }
    if (isChanged) {
        onChange(currencyDbEntity.copy(isActive = isActive.value))
    }
    currencyDbEntity.apply {
        Card(
            modifier = modifier.padding(5.dp)
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(2f)
                ) {
                    Text(text = abbreviation)
                    Text(text = name)
                }
                Switch(
                    checked = isActive.value,
                    onCheckedChange = {
                        isActive.value = !isActive.value
                        //onChange(currencyDbEntity.copy(isActive = it))
                    }
                )
                IconButton(
                    onClick = {
                        // TODO DRAG onChange()
                    }
                ) {
                    Icon(
                        painterResource(id = MenuAction.Menu.icon),
                        stringResource(id = MenuAction.Menu.label)
                    )
                }
            }
        }
    }
}
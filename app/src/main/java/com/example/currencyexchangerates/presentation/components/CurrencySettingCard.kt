package com.example.currencyexchangerates.presentation.components

import android.util.Log
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.currencyexchangerates.common.MenuAction
import com.example.currencyexchangerates.data.remote.dto.CurrencyDto
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity
import org.burnoutcrew.reorderable.ReorderableState
import org.burnoutcrew.reorderable.detectReorderAfterLongPress

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CurrencySettingCard(
    currencyDbEntity: CurrencyDbEntity,
    onChange: (CurrencyDbEntity) -> Unit,
    modifier: Modifier = Modifier,
    reorderState: ReorderableState? = null
) {
    val isActive = remember { mutableStateOf(currencyDbEntity.isActive) }

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
                        onChange(currencyDbEntity.copy(isActive = isActive.value))
                    }
                )
                IconButton(
                    onClick = {},
                    modifier = if (reorderState != null) Modifier.detectReorderAfterLongPress(reorderState) else Modifier
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
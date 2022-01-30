package com.example.currencyexchangerates.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyexchangerates.data.remote.dto.CurrencyDto
import com.example.currencyexchangerates.domain.model.CurrencyDbEntity

@Composable
fun CurrencyCard(
    currency: CurrencyDbEntity,
    extraOfficialRate: Double? = null,
    modifier: Modifier = Modifier
) {
    currency.apply {
        Card(
            modifier = modifier.padding(5.dp)
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(1.8f)
                ) {
                    Text(text = abbreviation)
                    Text(text = "$scale $name")
                }
                Row(
                    modifier = Modifier.weight(1.2f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "%.4f".format(rate))
                    if (extraOfficialRate != null) {
                        Text(text = "%.4f".format(extraOfficialRate))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGG() {
    CurrencyCard(CurrencyDbEntity("", 20, "", 4.2, 1, true))
}
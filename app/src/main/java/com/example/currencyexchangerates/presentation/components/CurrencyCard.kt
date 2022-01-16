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

@Composable
fun CurrencyCard(
    currencyDto: CurrencyDto,
    extraOfficialRate: List<Double>? = null,
    modifier: Modifier = Modifier
) {
    currencyDto.apply {
        Card(
            modifier = modifier.padding(5.dp)
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(2f)
                ) {
                    Text(text = Cur_Abbreviation)
                    Text(text = "$Cur_Scale $Cur_Name")
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "%.4f".format(Cur_OfficialRate))
                    if (extraOfficialRate != null) {
                        for (extraRate in extraOfficialRate) {
                            Text(text = "%.4f".format(extraRate))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGG() {
    CurrencyCard(CurrencyDto("", 20, "", 4.2, 1, ""))
}
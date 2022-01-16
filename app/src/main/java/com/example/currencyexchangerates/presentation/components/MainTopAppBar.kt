package com.example.currencyexchangerates.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.currencyexchangerates.common.MenuAction

@Composable
fun MainTopAppBar(
    title: String,
    navigationIcon: MenuAction? = null,
    actions: List<MenuAction>? = null,
    onNavigateClick: (Int) -> Unit,
    onActionClick: (Int) -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        modifier = Modifier,
        navigationIcon = {
            if (navigationIcon != null) {
                IconButton(onClick = { onNavigateClick(navigationIcon.label) }) {
                    Icon(
                        painterResource(id = navigationIcon.icon),
                        stringResource(id = navigationIcon.label)
                    )
                }
            }
        },
        actions = {
            if (actions != null) {
                for (action in actions) {
                    IconButton(
                        onClick = {
                            onActionClick(action.label)
                        }
                    ) {
                        Icon(
                            painterResource(id = action.icon),
                            stringResource(id = action.label)
                        )
                    }
                }
            }
        }
    )
}
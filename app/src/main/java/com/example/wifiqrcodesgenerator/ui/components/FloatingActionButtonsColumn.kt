package com.example.wifiqrcodesgenerator.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.wifiqrcodesgenerator.ui.theme.WifiQRCodesGeneratorTheme

@Composable
fun FloatingActionButtonsColumn(
    itemsCount: Int,
    navigateToReorderItems: () -> Unit,
    navigateToAddItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
    ) {
        AnimatedVisibility(
            visible = itemsCount > 1,
            enter = slideInHorizontally { it },
            exit = slideOutHorizontally { it }
        ) {
            FloatingActionButton(onClick = navigateToReorderItems,) {
                Icon(
                    imageVector = Icons.Default.Reorder,
                    contentDescription = null
                )
            }
        }
        FloatingActionButton(onClick = navigateToAddItem) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun FloatingActionButtonsPreview() {
    WifiQRCodesGeneratorTheme {
        Surface {
            FloatingActionButtonsColumn(
                itemsCount = 7,
                navigateToReorderItems = { },
                navigateToAddItem = { }
            )
        }
    }
}
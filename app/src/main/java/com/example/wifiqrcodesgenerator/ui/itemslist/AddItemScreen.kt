package com.example.wifiqrcodesgenerator.ui.itemslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.wifiqrcodesgenerator.navigation.Destinations
import com.example.wifiqrcodesgenerator.ui.components.TextFieldsColumn
import com.example.wifiqrcodesgenerator.ui.theme.WifiQRCodesGeneratorTheme

fun NavGraphBuilder.addItem(
	navController: NavController,
	viewModel: ItemsListViewModel
) {
	composable(Destinations.ADD_ITEM_ROUTE) {
		AddItemScreen(
			addItem = viewModel::addItem,
			navigateUp = navController::navigateUp,
			modifier = Modifier.fillMaxSize()
		)
	}
}

fun NavController.navigateToAddItem() {
	navigate(Destinations.ADD_ITEM_ROUTE) {
		launchSingleTop = true
	}
}

@Composable
fun AddItemScreen(
	addItem: (String, String) -> Unit,
	navigateUp: () -> Unit,
	modifier: Modifier = Modifier
) {
	Box(modifier = modifier) {
		val focusManager = LocalFocusManager.current
		var ssid by remember { mutableStateOf("") }
		val updateSsid: (String) -> Unit = { value -> ssid = value }
		var password by remember { mutableStateOf("") }
		val updatePassword: (String) -> Unit = { value -> password = value }
		TextFieldsColumn(
			ssid = ssid,
			updateSsid = updateSsid,
			password = password,
			updatePassword = updatePassword,
			clearFocus = focusManager::clearFocus,
			modifier = Modifier.padding(24.dp)
		)
		val action: () -> Unit = if (ssid.isNotBlank()) {
			{
				addItem(ssid, password)
				navigateUp()
			}
		} else {
			navigateUp
		}
		val icon = if (ssid.isNotBlank()) Icons.Default.Check else Icons.Default.ArrowBack
		FloatingActionButton(
			onClick = action,
			modifier = Modifier
				.padding(end = 24.dp, bottom = 24.dp)
				.align(Alignment.BottomEnd)
		) {
			Icon(
				imageVector = icon,
				contentDescription = null
			)
		}
	}
}

@Preview
@Composable
fun AddItemScreenPreview() {
	WifiQRCodesGeneratorTheme {
		Surface {
			AddItemScreen(
				addItem = { _, _ -> },
				navigateUp = { }
			)
		}
	}
}
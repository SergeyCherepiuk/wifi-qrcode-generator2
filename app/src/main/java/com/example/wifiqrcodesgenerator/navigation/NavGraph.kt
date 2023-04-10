package com.example.wifiqrcodesgenerator.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.wifiqrcodesgenerator.ui.itemslist.ItemsListViewModel
import com.example.wifiqrcodesgenerator.ui.itemslist.addItem
import com.example.wifiqrcodesgenerator.ui.itemslist.itemsList
import com.example.wifiqrcodesgenerator.ui.itemslist.reorderItems
import com.example.wifiqrcodesgenerator.ui.theme.WifiQRCodesGeneratorTheme

@Composable
fun NavGraph(navController: NavHostController) {
	val context = LocalContext.current
	val itemsListViewModel = ItemsListViewModel(context.applicationContext)
	WifiQRCodesGeneratorTheme {
		Surface(
			modifier = Modifier.fillMaxSize(),
			color = MaterialTheme.colorScheme.background
		) {
			NavHost(
				navController = navController,
				startDestination = Destinations.ITEMS_LIST_ROUTE
			) {
				itemsList(
					navController = navController,
					viewModel = itemsListViewModel
				)
				reorderItems(
					navController = navController,
					viewModel = itemsListViewModel
				)
				addItem(
					navController = navController,
					viewModel = itemsListViewModel
				)
			}
		}
	}
}
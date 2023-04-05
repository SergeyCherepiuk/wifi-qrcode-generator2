package com.example.wifiqrcodesgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.wifiqrcodesgenerator.ui.itemslist.ItemsListScreen
import com.example.wifiqrcodesgenerator.ui.itemslist.ItemsListViewModel
import com.example.wifiqrcodesgenerator.ui.theme.WifiQRCodesGeneratorTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val itemsListViewModel = ItemsListViewModel()
			WifiQRCodesGeneratorTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					val uiState by itemsListViewModel.uiState.collectAsState()
					ItemsListScreen(
						uiState = uiState,
						addItem = itemsListViewModel::addItem,
						updateItem = itemsListViewModel::updateItem,
						deleteItem = itemsListViewModel::deleteItem
					)
				}
			}
		}
	}
}
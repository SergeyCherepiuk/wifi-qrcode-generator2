package com.example.wifiqrcodesgenerator.ui.itemslist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ItemsListViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(ItemsListUiState())
	val uiState: StateFlow<ItemsListUiState> = _uiState.asStateFlow()

	fun addItem(
		ssid: String,
        password: String
	) {
		val items = _uiState.value.items.toMutableList()
		val item = ItemUiState(
			ssid = ssid,
			password = password
		)
		items.add(item)
		_uiState.update { it.copy(
			items = items
		) }
	}

	fun updateItem(
		item: ItemUiState,
		ssid: String,
		password: String
	) {
		val items = _uiState.value.items.toMutableList()
		val index = items.indexOf(item)
		items[index] = items[index].copy(
			ssid = ssid,
			password = password
		)
		_uiState.update { it.copy(
			items = items
		) }
	}

	fun deleteItem(item: ItemUiState) {
		val items = _uiState.value.items.toMutableList()
		items.remove(item)
		_uiState.update { it.copy(
			items = items
		) }
	}

	fun reorderItems(from: Int, to: Int) {
		val items = _uiState.value.items.toMutableList()
		items[from] = items[to].also { items[to] = items[from] }
		_uiState.update { it.copy(
			items = items
		) }
	}
}
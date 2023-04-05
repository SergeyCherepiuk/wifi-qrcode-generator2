package com.example.wifiqrcodesgenerator.ui.itemslist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ItemsListViewModel : ViewModel() {
	private val _uiState = MutableStateFlow(ItemsListUiState())
	val uiState: StateFlow<ItemsListUiState> = _uiState.asStateFlow()

	fun addItem() {
		val items = _uiState.value.items.toMutableList()
		val item = ItemUiState(
			ssid = java.util.UUID.randomUUID().toString().slice(1..10),
			password = java.util.UUID.randomUUID().toString().slice(1..10)
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
}
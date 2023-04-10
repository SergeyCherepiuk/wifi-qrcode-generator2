package com.example.wifiqrcodesgenerator.ui.itemslist

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifiqrcodesgenerator.database.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemsListViewModel(context: Context) : ViewModel() {
	private val database: AppDatabase = AppDatabase.getInstance(context)
	private val _uiState = MutableStateFlow(ItemsListUiState())
	val uiState: StateFlow<ItemsListUiState> = _uiState.asStateFlow()

	init {
		_uiState.update { it.copy(
			isLoading = true
		) }
		viewModelScope.launch {
			_uiState.update { it.copy(
				items = database.itemDao().getItems(),
				isLoading = false
			) }
		}
	}

	fun addItem(ssid: String, password: String) {
		val item = ItemUiState(
			ssid = ssid,
			password = password
		)
		viewModelScope.launch {
			database.itemDao().addItem(item)
			_uiState.update { it.copy(
				items = database.itemDao().getItems()
			) }
		}
	}

	fun updateItem(item: ItemUiState) {
		viewModelScope.launch {
			database.itemDao().updateItem(item)
			_uiState.update { it.copy(
				items = database.itemDao().getItems()
			) }
		}
	}

	fun deleteItem(item: ItemUiState) {
		viewModelScope.launch {
			database.itemDao().deleteItem(item)
			_uiState.update { it.copy(
				items = database.itemDao().getItems()
			) }
		}
	}

	fun reorderItems(from: Int, to: Int) {
		val items = _uiState.value.items.toMutableList()
		items[from].id = items[to].id.also { items[to].id = items[from].id }
		items[from] = items[to].also { items[to] = items[from] }
		_uiState.update { it.copy(
			items = items
		) }
	}

	fun submitReorderItems() {
		val items = _uiState.value.items.toMutableList()
		viewModelScope.launch {
			database.itemDao().updateItems(items)
			_uiState.update { it.copy(
				items = database.itemDao().getItems()
			) }
		}
	}
}
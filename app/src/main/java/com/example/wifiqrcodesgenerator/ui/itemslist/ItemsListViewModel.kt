package com.example.wifiqrcodesgenerator.ui.itemslist

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifiqrcodesgenerator.data.ItemsRepository
import com.example.wifiqrcodesgenerator.models.QRCode
import com.example.wifiqrcodesgenerator.utils.generateBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ItemsListViewModel(
	private val itemsRepository: ItemsRepository
) : ViewModel() {
	private val _uiState = MutableStateFlow(ItemsListUiState())
	val uiState: StateFlow<ItemsListUiState> = _uiState.asStateFlow()

	init {
		_uiState.update { it.copy(
			isLoading = true
		) }
		viewModelScope.launch {
			itemsRepository.items
				.flowOn(Dispatchers.IO)
				.collect { items ->
					_uiState.update { it.copy(
						items = items,
						isLoading = false
					) }
				}
		}
	}

	fun addItem(ssid: String, password: String) {
		val item = QRCode(
			ssid = ssid.trim(),
			password = password.trim()
		)
		viewModelScope.launch {
			itemsRepository.addItem(item)
		}
	}

	fun updateItem(item: QRCode, ssid: String, password: String) {
		val updatedItem = item.copy(
			ssid = ssid.trim(),
			password = password.trim()
		)
		viewModelScope.launch {
			itemsRepository.updateItem(updatedItem)
		}
	}

	fun deleteItem(item: QRCode) {
		viewModelScope.launch {
			itemsRepository.deleteItem(item)
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

	fun submitReorderedItems() {
		val items = _uiState.value.items.toMutableList()
		viewModelScope.launch {
			itemsRepository.updateItems(items)
		}
	}

	fun shareImage(context: Context, item: QRCode) {
		val bitmap: Bitmap = generateBitmap(item.ssid, item.password)
		val image = File(
			Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
			"/last-shared-image.jpg"
		)
		val uri: Uri = FileProvider.getUriForFile(
			context,
			"com.example.wifiqrcodesgenerator.provider",
			image
		)

		try {
			FileOutputStream(image).also {
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
				it.flush()
				it.close()
			}
			Intent().apply {
				action = Intent.ACTION_SEND
				type = "image/jpeg"
				putExtra(Intent.EXTRA_STREAM, uri)
			}.also {
				ContextCompat.startActivity(context, it, null)
			}
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}
}
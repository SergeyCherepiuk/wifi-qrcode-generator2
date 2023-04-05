package com.example.wifiqrcodesgenerator.ui.itemslist

import android.graphics.Bitmap
import com.example.wifiqrcodesgenerator.utils.QRCodeGenerator

data class ItemUiState(
	var ssid: String,
	var password: String
)

fun ItemUiState.toBitmap(): Bitmap = QRCodeGenerator.generate(this.ssid, this.password)

data class ItemsListUiState(
	val items: List<ItemUiState> = emptyList(),
	val currentPageIndex: Int = 0
)
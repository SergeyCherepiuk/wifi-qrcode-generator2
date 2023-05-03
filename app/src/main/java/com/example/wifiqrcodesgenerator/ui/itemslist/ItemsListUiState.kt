package com.example.wifiqrcodesgenerator.ui.itemslist

import com.example.wifiqrcodesgenerator.models.QRCode


data class ItemsListUiState(
	val items: List<QRCode> = emptyList(),
	val isLoading: Boolean = false
)
package com.example.wifiqrcodesgenerator.ui.itemslist

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wifiqrcodesgenerator.utils.QRCodeGenerator

@Entity(tableName = "item")
data class ItemUiState(
	@PrimaryKey(autoGenerate = true) var id: Int? = null,
	@ColumnInfo var ssid: String,
	@ColumnInfo var password: String,
)

fun ItemUiState.toBitmap(): Bitmap = QRCodeGenerator.generate(this.ssid, this.password)

data class ItemsListUiState(
	val items: List<ItemUiState> = emptyList(),
	val isLoading: Boolean = false
)
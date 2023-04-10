package com.example.wifiqrcodesgenerator.database

import androidx.room.*
import com.example.wifiqrcodesgenerator.ui.itemslist.ItemUiState

@Dao
interface ItemDao {
	@Query("SELECT * FROM item")
	suspend fun getItems(): List<ItemUiState>

	@Insert
	suspend fun addItem(item: ItemUiState)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	suspend fun updateItem(item: ItemUiState)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	suspend fun updateItems(items: List<ItemUiState>)

	@Delete
	suspend fun deleteItem(item: ItemUiState)
}
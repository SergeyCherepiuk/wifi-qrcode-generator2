package com.example.wifiqrcodesgenerator.database

import androidx.room.*
import com.example.wifiqrcodesgenerator.models.QRCode
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
	@Query("SELECT * FROM qrcode")
	fun getItems(): Flow<List<QRCode>>

	@Insert
	suspend fun addItem(item: QRCode)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	suspend fun updateItem(item: QRCode)

	@Update(onConflict = OnConflictStrategy.REPLACE)
	suspend fun updateItems(items: List<QRCode>)

	@Delete
	suspend fun deleteItem(item: QRCode)
}
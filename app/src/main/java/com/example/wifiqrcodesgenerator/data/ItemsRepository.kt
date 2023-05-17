package com.example.wifiqrcodesgenerator.data

import com.example.wifiqrcodesgenerator.models.QRCode
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    val items: Flow<List<QRCode>>
    suspend fun addItem(item: QRCode)
    suspend fun updateItem(item: QRCode)
    suspend fun updateItems(items: List<QRCode>)
    suspend fun deleteItem(item: QRCode)
}
package com.example.wifiqrcodesgenerator.data

import com.example.wifiqrcodesgenerator.database.AppDatabase
import com.example.wifiqrcodesgenerator.models.QRCode
import kotlinx.coroutines.flow.Flow

class ItemsRepositoryImpl(
    private val databaseDataSource: AppDatabase,
) : ItemsRepository {
    override val items: Flow<List<QRCode>> = databaseDataSource.itemDao().getItems()

    override suspend fun addItem(item: QRCode) {
        databaseDataSource.itemDao().addItem(item)
    }

    override suspend fun updateItem(item: QRCode) {
        databaseDataSource.itemDao().updateItem(item)
    }

    override suspend fun updateItems(items: List<QRCode>) {
        databaseDataSource.itemDao().updateItems(items)
    }

    override suspend fun deleteItem(item: QRCode) {
        databaseDataSource.itemDao().deleteItem(item)
    }
}
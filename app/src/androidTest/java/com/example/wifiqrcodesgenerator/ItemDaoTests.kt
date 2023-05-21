package com.example.wifiqrcodesgenerator

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.wifiqrcodesgenerator.database.AppDatabase
import com.example.wifiqrcodesgenerator.database.ItemDao
import com.example.wifiqrcodesgenerator.models.QRCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ItemDaoTests {
    private lateinit var database: AppDatabase
    private lateinit var itemDao: ItemDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        itemDao = database.itemDao()
    }

    @Test
    fun getItemsTest() = runBlocking {
        val items: List<QRCode> = listOf(
            QRCode(id = 1, ssid = "sample-ssid1", password = "sample-password1"),
            QRCode(id = 2, ssid = "sample-ssid2", password = "sample-password2"),
        )
        items.forEach { item -> itemDao.addItem(item) }
        itemDao.getItems()
            .flowOn(Dispatchers.IO)
            .collect {
                assert(it == items)
            }
    }

    @Test
    fun addItemTest() = runBlocking {
        val item = QRCode(id = 1, ssid = "sample-ssid", password = "sample-password")
        itemDao.addItem(item)
        itemDao.getItems()
            .flowOn(Dispatchers.IO)
            .collect {
                assert(it.contains(item))
            }
    }

    @Test
    fun updateItemTest() = runBlocking {
        val item = QRCode(id = 1, ssid = "sample-ssid", password = "sample-password")
        itemDao.addItem(item)
        val updatedItem = QRCode(id = 1, ssid = "new-sample-ssid", password = "new-sample-password")
        itemDao.updateItem(updatedItem)
        itemDao.getItems()
            .flowOn(Dispatchers.IO)
            .collect {
                val notContainsOldItem = it.contains(updatedItem)
                val containsUpdatedItem = !it.contains(item)
                assert(notContainsOldItem && containsUpdatedItem)
            }
    }

    @Test
    fun updateItemsTest() = runBlocking {
        val items: List<QRCode> = listOf(
            QRCode(id = 1, ssid = "sample-ssid1", password = "sample-password1"),
            QRCode(id = 2, ssid = "sample-ssid2", password = "sample-password2"),
        )
        items.forEach { item -> itemDao.addItem(item) }
        val updatedItems = items.map { item ->
            item.copy(ssid = "new-${item.ssid}", password = "new-${item.password}")
        }
        itemDao.updateItems(updatedItems)
        itemDao.getItems()
            .flowOn(Dispatchers.IO)
            .collect {
                val notContainsOldItems = items.all { allItems -> !it.contains(allItems) }
                val containsUpdatedItems = updatedItems.all { allItems -> it.contains(allItems) }
                assert(notContainsOldItems && containsUpdatedItems)
            }
    }

    @Test
    fun deleteItemTest() = runBlocking {
        val item = QRCode(id = 1, ssid = "sample-ssid", password = "sample-password")
        itemDao.addItem(item)
        itemDao.deleteItem(item)
        itemDao.getItems()
            .flowOn(Dispatchers.IO)
            .collect {
                assert(!it.contains(item))
            }
    }

    @After
    fun closeDatabase() {
        database.close()
    }
}
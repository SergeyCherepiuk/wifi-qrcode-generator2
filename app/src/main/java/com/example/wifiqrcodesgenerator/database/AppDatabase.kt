package com.example.wifiqrcodesgenerator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wifiqrcodesgenerator.ui.itemslist.ItemUiState

@Database(entities = [ItemUiState::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
	abstract fun itemDao(): ItemDao

	companion object {
		@Volatile
		private var INSTANCE: AppDatabase? = null
		fun getInstance(context: Context): AppDatabase {
			synchronized(this) {
				var instance = INSTANCE
				if (instance == null) {
					instance = Room.databaseBuilder(
						context = context.applicationContext,
						klass = AppDatabase::class.java,
						name = "database"
					).build()
					INSTANCE = instance
				}
				return instance
			}
		}
	}
}
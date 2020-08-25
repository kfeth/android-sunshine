package com.kfeth.sunshine.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Weather::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}
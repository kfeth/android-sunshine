package com.kfeth.sunshine.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(DateConverter::class)
@Database(
    entities = [
        WeatherLocation::class,
        CurrentWeather::class,
        ForecastWeather::class,
        Favourites::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
}

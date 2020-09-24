package com.kfeth.sunshine.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kfeth.sunshine.data.entity.CurrentWeather
import com.kfeth.sunshine.data.entity.Favourites
import com.kfeth.sunshine.data.entity.ForecastWeather
import com.kfeth.sunshine.data.entity.WeatherLocation

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

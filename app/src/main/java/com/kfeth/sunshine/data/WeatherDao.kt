package com.kfeth.sunshine.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather ORDER BY id DESC")
    fun getAll(): Flow<List<Weather>>

    @Query("SELECT * FROM weather WHERE lat = :lat AND lon = :lon")
    fun get(lat: Double, lon: Double): Flow<Weather>

    @Insert
    suspend fun insert(weather: Weather): Long
}
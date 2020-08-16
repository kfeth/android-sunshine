package com.kfeth.sunshine.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather ORDER BY id DESC")
    fun getAll(): List<Weather>
}
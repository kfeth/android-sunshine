package com.kfeth.sunshine.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kfeth.sunshine.data.entity.WeatherLocation
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations WHERE queryString LIKE :query || '%' ORDER BY name")
    fun searchLocations(query: String): Flow<List<WeatherLocation>>

    @Query("SELECT * FROM locations WHERE id = :weatherId")
    fun getLocation(weatherId: Int): Flow<WeatherLocation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<WeatherLocation>)
}

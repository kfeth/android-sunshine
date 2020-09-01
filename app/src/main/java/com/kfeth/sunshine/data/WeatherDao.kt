package com.kfeth.sunshine.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM locations WHERE queryString LIKE :query || '%' ORDER BY name")
    fun searchLocations(query: String): Flow<List<WeatherLocation>>

    @Query("SELECT * FROM locations WHERE id = :weatherId")
    fun getWeatherLocation(weatherId: Int): Flow<WeatherLocation>

    @Query("SELECT * FROM weather WHERE id = :weatherId")
    fun getCurrentWeather(weatherId: Int): Flow<CurrentWeather>

    @Query("SELECT * FROM forecast WHERE weatherId = :weatherId")
    fun getForecast(weatherId: Int): Flow<List<ForecastWeather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<WeatherLocation>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeather(currentWeather: CurrentWeather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: List<ForecastWeather>)

    @Query("DELETE FROM forecast WHERE weatherId = :weatherId")
    suspend fun deleteForecast(weatherId: Int)
}
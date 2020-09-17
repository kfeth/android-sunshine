package com.kfeth.sunshine.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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

    @Query("SELECT EXISTS(SELECT * FROM favourites WHERE weatherId = :weatherId)")
    fun isFavourite(weatherId: Int?): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(favourite: Favourites)

    @Query("DELETE FROM favourites WHERE weatherId = :weatherId")
    suspend fun removeFavourite(weatherId: Int)

    @Query("SELECT l.name, l.addressString, l.latitude, l.longitude, l.countryCode, " +
            "w.id, w.iconId, w.description, w.temperature FROM weather AS w " +
            "INNER JOIN locations AS l ON w.id = l.id " +
            "INNER JOIN favourites AS f ON w.id = f.weatherId ORDER BY l.name")
    fun getFavourites(): Flow<List<FavouriteItem>>

    @Update(entity = CurrentWeather::class)
    fun updateWeather(update: List<WeatherUpdate>?)
}
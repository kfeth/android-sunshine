package com.kfeth.sunshine.data

import com.kfeth.sunshine.BuildConfig.REVERSE_GEOCODE_API_URL
import com.kfeth.sunshine.api.GeocodeService
import com.kfeth.sunshine.api.WeatherService
import com.kfeth.sunshine.data.api.GeocodeResponse
import com.kfeth.sunshine.data.api.asCurrentWeather
import com.kfeth.sunshine.data.api.asForecast
import com.kfeth.sunshine.data.api.asLocations
import com.kfeth.sunshine.data.api.asWeatherUpdate
import com.kfeth.sunshine.data.db.LocationDao
import com.kfeth.sunshine.data.db.WeatherDao
import com.kfeth.sunshine.data.entity.Favourites
import com.kfeth.sunshine.data.entity.joinIdsToString
import com.kfeth.sunshine.utils.networkBoundResource
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val locationDao: LocationDao,
    private val weatherService: WeatherService,
    private val geocodeService: GeocodeService
) {

    fun searchWeatherLocations(query: String) = networkBoundResource(
        shouldFetch = { query.length >= 3 },
        query = { locationDao.searchLocations(query) },
        fetch = {
            delay(750)
            weatherService.searchLocations(query)
        },
        saveFetchResult = {
            locationDao.insertLocations(it.asLocations(query).map { item ->
                item.copy(addressString = reverseGeocode(item.latitude, item.longitude).address)
            })
        }
    )

    fun getWeatherDetails(weatherId: Int) = networkBoundResource(
        query = { weatherDao.getCurrentWeather(weatherId) },
        fetch = {
            val location = locationDao.getLocation(weatherId).first()
            weatherService.weatherForLocation(location.latitude, location.longitude)
        },
        saveFetchResult = {
            weatherDao.insertCurrentWeather(it.asCurrentWeather(weatherId))
            weatherDao.deleteForecast(weatherId)
            weatherDao.insertForecast(it.asForecast(weatherId))
        }
    )

    fun getWeatherForFavourites() = networkBoundResource(
        shouldFetch = { !it.isNullOrEmpty() },
        query = { weatherDao.getFavourites() },
        fetch = {
            weatherService.weatherForIds(weatherDao.getFavourites().first().joinIdsToString())
        },
        saveFetchResult = { weatherDao.updateWeather(it.asWeatherUpdate()) }
    )

    fun getWeatherLocation(weatherId: Int) = locationDao.getLocation(weatherId)

    fun getForecast(weatherId: Int) = weatherDao.getForecast(weatherId)

    private suspend fun reverseGeocode(latitude: Double, longitude: Double): GeocodeResponse {
        val url = REVERSE_GEOCODE_API_URL.format(latitude, longitude)
        return geocodeService.reverseGeocode(url).body()!!
    }

    suspend fun toggleFavourite(weatherId: Int) {
        if (isFavourite(weatherId).first()) {
            weatherDao.removeFavourite(weatherId)
        } else {
            weatherDao.addFavourite(Favourites(weatherId))
        }
    }

    fun isFavourite(weatherId: Int): Flow<Boolean> {
        return weatherDao.isFavourite(weatherId)
    }
}

package com.kfeth.sunshine.data

import com.kfeth.sunshine.BuildConfig.REVERSE_GEOCODE_API_URL
import com.kfeth.sunshine.api.GeocodeService
import com.kfeth.sunshine.api.WeatherService
import com.kfeth.sunshine.utilities.DEBOUNCE_DELAY_MILLIS
import com.kfeth.sunshine.utilities.Resource
import com.kfeth.sunshine.utilities.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService,
    private val geocodeService: GeocodeService
) {

    fun searchWeatherLocations(query: String) = networkBoundResource(
        shouldFetch = { query.length >= 3 },
        query = { weatherDao.searchLocations(query) },
        fetch = {
            delay(DEBOUNCE_DELAY_MILLIS)
            weatherService.searchLocations(query)
        },
        saveFetchResult = { searchResponse ->
            // TODO Refactor logic for creating entity
            val locationsList = searchResponse.list.map {
                val geoCodeResponse = reverseGeocode(it.coordinates.latitude, it.coordinates.longitude)
                WeatherLocation(query, it, geoCodeResponse)
            }
            weatherDao.insertLocations(locationsList)
        }
    )

    fun getWeatherDetails(weatherId: Int, lat: Double, lon: Double) = networkBoundResource(
        query = { weatherDao.getCurrentWeather(weatherId) },
        fetch = { weatherService.weatherDetails(lat, lon) },
        saveFetchResult = {
            weatherDao.deleteForecast(weatherId)
            weatherDao.insertCurrentWeather(it.asCurrentWeather(weatherId))
            weatherDao.insertForecast(it.asForecast(weatherId))
        }
    )

    fun getWeatherLocation(weatherId: Int) = weatherDao.getWeatherLocation(weatherId)

    fun getForecast(weatherId: Int) = weatherDao.getForecast(weatherId)

    private suspend fun reverseGeocode(latitude: Double, longitude: Double): GeocodeResponse {
        val url = REVERSE_GEOCODE_API_URL.format(latitude, longitude)
        return geocodeService.reverseGeocode(url).body()!!
    }

    suspend fun toggleFavourite(weatherId: Int) {
        if (weatherDao.isFavourite(weatherId).first()) {
            weatherDao.removeFavourite(weatherId)
        } else {
            weatherDao.addFavourite(Favourites(weatherId))
        }
    }

    fun isFavourite(weatherId: Int): Flow<Boolean> {
        return weatherDao.isFavourite(weatherId)
    }
}
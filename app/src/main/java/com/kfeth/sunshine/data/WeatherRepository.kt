package com.kfeth.sunshine.data

import com.kfeth.sunshine.BuildConfig.REVERSE_GEOCODE_API_URL
import com.kfeth.sunshine.api.GeocodeService
import com.kfeth.sunshine.api.WeatherService
import com.kfeth.sunshine.utilities.DEBOUNCE_DELAY_MILLIS
import com.kfeth.sunshine.utilities.Resource
import com.kfeth.sunshine.utilities.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService,
    private val geocodeService: GeocodeService
) {

    fun searchWeatherLocations(query: String): Flow<Resource<List<WeatherLocation>>> = networkBoundResource(
        shouldFetch = { query.length >= 3 },
        query = { weatherDao.searchLocations(query) },
        fetch = {
            delay(DEBOUNCE_DELAY_MILLIS)
            weatherService.searchLocations(query)
        },
        saveFetchResult = { searchResponse ->
            val locationsList = searchResponse.list.map {
                val geoCodeResponse = reverseGeocode(it.coordinates.latitude, it.coordinates.longitude)
                WeatherLocation(query, it, geoCodeResponse)
            }
            weatherDao.bulkInsert(locationsList)
        }
    )

    fun getCurrentWeather(weatherId: Int): Flow<Resource<CurrentWeather>> = networkBoundResource(
        shouldFetch = { true },
        query = { weatherDao.getCurrentWeather(weatherId) },
        fetch = { weatherService.currentWeather(weatherId) },
        saveFetchResult = { weatherDao.insert(CurrentWeather(it)) }
    )

    fun getWeatherLocation(weatherId: Int): Flow<WeatherLocation> {
        return weatherDao.getWeatherLocation(weatherId)
    }

    private suspend fun reverseGeocode(latitude: Double, longitude: Double): GeocodeResponse {
        val url = REVERSE_GEOCODE_API_URL.format(latitude, longitude)
        return geocodeService.reverseGeocode(url).body()!!
    }
}
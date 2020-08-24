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

    fun getWeather(lat: Double, lng: Double): Flow<Resource<Weather>> = networkBoundResource(
        query = { weatherDao.get(lat, lng) },
        fetch = { weatherService.getWeatherForecast(lat, lng) },
        saveFetchResult = { weatherDao.insert(Weather(it.lat, it.lon, it.timezone)) },
    )

    fun searchLocations(query: String): Flow<Resource<List<Location>>> = networkBoundResource(
        shouldFetch = { query.length >= 3 },
        query = { weatherDao.searchLocations(query) },
        fetch = {
            delay(DEBOUNCE_DELAY_MILLIS)
            weatherService.searchLocations(query)
        },
        saveFetchResult = { searchResponse ->
            val locations = searchResponse.list.map {
                Location(query, it, reverseGeocode(it.coordinates.latitude, it.coordinates.longitude))
            }
            weatherDao.bulkInsert(locations)
        }
    )

    private suspend fun reverseGeocode(latitude: Double, longitude: Double): GeocodeResponse {
        val url = REVERSE_GEOCODE_API_URL.format(latitude, longitude)
        return geocodeService.reverseGeocode(url).body()!!
    }
}
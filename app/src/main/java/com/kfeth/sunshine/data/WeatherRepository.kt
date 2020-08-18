package com.kfeth.sunshine.data

import com.kfeth.sunshine.api.WeatherService
import com.kfeth.sunshine.utilities.Resource
import com.kfeth.sunshine.utilities.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherService: WeatherService
) {

    fun getWeather(lat: Double, lng: Double): Flow<Resource<Weather>> = networkBoundResource(
        query = { weatherDao.get(lat, lng) },
        fetch = { weatherService.getWeatherForecast(lat, lng) },
        saveFetchResult = { weatherDao.insert(Weather(it.lat, it.lon, it.timezone)) },
        shouldFetch = { true }
    )

    val weatherList = weatherDao.getAll()
}
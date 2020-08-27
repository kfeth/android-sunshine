package com.kfeth.sunshine.api

import com.kfeth.sunshine.BuildConfig.WEATHER_API_KEY
import com.kfeth.sunshine.data.CurrentWeatherResponse
import com.kfeth.sunshine.data.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("find?type=like&sort=population&units=metric&appid=$WEATHER_API_KEY")
    suspend fun searchLocations(
        @Query("q") query: String
    ): Response<SearchResponse>

    @GET("weather?units=metric&appid=$WEATHER_API_KEY")
    suspend fun currentWeather(
        @Query("id") id: Int
    ): Response<CurrentWeatherResponse>
}
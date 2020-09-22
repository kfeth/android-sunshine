package com.kfeth.sunshine.api

import com.kfeth.sunshine.BuildConfig.WEATHER_API_KEY
import com.kfeth.sunshine.data.WeatherDetailsResponse
import com.kfeth.sunshine.data.WeatherSummaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("find?type=like&sort=population&units=metric&appid=$WEATHER_API_KEY")
    suspend fun searchLocations(
        @Query("q") query: String
    ): Response<WeatherSummaryResponse>

    @GET("onecall?units=metric&exclude=hourly,minutely&appid=$WEATHER_API_KEY")
    suspend fun weatherForLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Response<WeatherDetailsResponse>

    @GET("group?units=metric&appid=$WEATHER_API_KEY")
    suspend fun weatherForIds(
        @Query("id", encoded = true) ids: String
    ): Response<WeatherSummaryResponse>
}
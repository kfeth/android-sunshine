package com.kfeth.sunshine.api

import com.kfeth.sunshine.BuildConfig.API_KEY
import com.kfeth.sunshine.data.SearchResponse
import com.kfeth.sunshine.data.WeatherResp
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("onecall?exclude=hourly,minutely&appid=$API_KEY")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): Response<WeatherResp>

    @GET("find?type=like&sort=population&units=metric&appid=$API_KEY")
    suspend fun searchLocations(
        @Query("q") query: String
    ): Response<SearchResponse>
}
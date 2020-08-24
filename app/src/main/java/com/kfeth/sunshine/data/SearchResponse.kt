package com.kfeth.sunshine.data

import com.squareup.moshi.Json

data class SearchResponse(val list: List<LocationResponse>)

data class LocationResponse(
    val id: Int,
    val name: String,
    @Json(name = "coord") val coordinates: CoordinatesResponse,
    @Json(name = "main") val condition: ConditionResponse,
    @Json(name = "weather") val weather: List<WeatherResponse>
)

data class CoordinatesResponse(
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double
)

data class ConditionResponse(
    val temp: Double
)

data class WeatherResponse(
    val icon: String
)

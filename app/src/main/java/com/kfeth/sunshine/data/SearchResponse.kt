package com.kfeth.sunshine.data

import com.squareup.moshi.Json

data class SearchResponse(val list: List<WeatherLocationResponse>)

data class WeatherLocationResponse(
    val id: Int,
    val name: String,
    @Json(name = "coord") val coordinates: CoordinatesResponse,
    @Json(name = "main") val temperature: TemperatureResponse,
    @Json(name = "weather") val conditions: List<ConditionResponse>
)

data class CoordinatesResponse(
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double
)

data class TemperatureResponse(
    val temp: Double
)

data class ConditionResponse(
    @Json(name = "icon") val iconId: String
)

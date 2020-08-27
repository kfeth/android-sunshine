package com.kfeth.sunshine.data

import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "list") val list: List<WeatherLocationResponse>
)

data class WeatherLocationResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "coord") val coordinates: CoordinatesResponse,
    @Json(name = "weather") val conditions: List<ConditionResponse>,
    @Json(name = "main") val environment: EnvironmentResponse
)

data class CoordinatesResponse(
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double
)

data class EnvironmentResponse(
    @Json(name = "temp") val temperature: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int,
)

data class ConditionResponse(
    @Json(name = "icon") val iconId: String,
    @Json(name = "description") val description: String
)

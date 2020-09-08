package com.kfeth.sunshine.data

import com.squareup.moshi.Json

data class WeatherSummaryResponse(
    @Json(name = "list") val list: List<WeatherResponse>
)

data class WeatherResponse(
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
    @Json(name = "temp") val temperature: Double
)

data class ConditionResponse(
    @Json(name = "icon") val iconId: String,
    @Json(name = "description") val description: String
)

fun WeatherSummaryResponse.asWeatherUpdate(): List<WeatherUpdate> {
    return list.map {
        WeatherUpdate(
            id = it.id,
            temperature = it.environment.temperature,
            iconId = it.conditions.first().iconId,
        )
    }
}
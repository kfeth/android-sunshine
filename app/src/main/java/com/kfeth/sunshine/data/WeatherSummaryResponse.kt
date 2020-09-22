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
    @Json(name = "main") val environment: EnvironmentResponse,
    @Json(name = "sys") val system: SystemResponse
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

data class SystemResponse(
    @Json(name = "country") val countryCode: String
)

fun WeatherSummaryResponse.asWeatherUpdate(): List<WeatherUpdate> {
    return list.map {
        WeatherUpdate(
            id = it.id,
            temperature = it.environment.temperature,
            iconId = it.conditions.first().iconId
        )
    }
}

fun WeatherSummaryResponse.asLocations(query: String): List<WeatherLocation> {
    return list.map {
        WeatherLocation(
            id = it.id,
            name = it.name,
            latitude = it.coordinates.latitude,
            longitude = it.coordinates.longitude,
            queryString = query,
            countryCode = it.system.countryCode,
            addressString = ""
        )
    }
}
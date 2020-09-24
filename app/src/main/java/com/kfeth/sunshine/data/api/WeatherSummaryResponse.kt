package com.kfeth.sunshine.data.api

import com.kfeth.sunshine.data.entity.WeatherLocation
import com.kfeth.sunshine.data.entity.WeatherUpdate
import com.squareup.moshi.Json

class WeatherSummaryResponse(
    @Json(name = "list") val list: List<WeatherResponse>
)

class WeatherResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "coord") val coordinates: CoordinatesResponse,
    @Json(name = "weather") val conditions: List<ConditionResponse>,
    @Json(name = "main") val environment: EnvironmentResponse,
    @Json(name = "sys") val system: SystemResponse
)

class CoordinatesResponse(
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double
)

class EnvironmentResponse(
    @Json(name = "temp") val temperature: Double
)

class ConditionResponse(
    @Json(name = "icon") val iconId: String,
    @Json(name = "description") val description: String
)

class SystemResponse(
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

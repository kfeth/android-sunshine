package com.kfeth.sunshine.data

import com.squareup.moshi.Json

data class CurrentWeatherResponse(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "weather") val conditions: List<ConditionResponse>,
    @Json(name = "main") val environment: EnvironmentResponse,
    @Json(name = "visibility") val visibility: Int,
    @Json(name = "wind") val wind: WindResponse,
    @Json(name = "dt") val date: Long,
    @Json(name = "sys") val system: SystemResponse
)

data class WindResponse(
    @Json(name = "speed") val speed: Double,
    @Json(name = "deg") val degrees: Int
)

data class SystemResponse(
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long
)

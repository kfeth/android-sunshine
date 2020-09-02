package com.kfeth.sunshine.data

import com.kfeth.sunshine.utilities.toOffsetDateTime
import com.squareup.moshi.Json
import java.util.Locale

data class WeatherDetailsResponse(
    @Json(name = "timezone_offset") val timezoneOffset: Int,
    @Json(name = "current") val current: CurrentWeatherResponse,
    @Json(name = "daily") val daily: List<ForecastResponse>,
)

data class CurrentWeatherResponse(
    @Json(name = "dt") val date: Long,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long,
    @Json(name = "temp") val temperature: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "uvi") val uvIndex: Double,
    @Json(name = "visibility") val visibility: Int,
    @Json(name = "wind_speed") val windSpeed: Double,
    @Json(name = "weather") val conditions: List<ConditionResponse>,
)

data class ForecastResponse(
    @Json(name = "dt") val date: Long,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long,
    @Json(name = "temp") val temperature: TemperatureResponse,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "wind_speed") val windSpeed: Double,
    @Json(name = "uvi") val uvIndex: Double,
    @Json(name = "weather") val conditions: List<ConditionResponse>
)

data class TemperatureResponse(
    @Json(name = "min") val min: Double,
    @Json(name = "max") val max: Double,
)

fun WeatherDetailsResponse.asCurrentWeather(weatherId: Int): CurrentWeather {
    return CurrentWeather(
        id = weatherId,
        iconId = current.conditions.first().iconId,
        description = current.conditions.first().description.capitalize(Locale.getDefault()),
        temperature = current.temperature,
        pressure = current.pressure,
        humidity = current.humidity,
        uvIndex = current.uvIndex,
        visibility = current.visibility,
        windSpeed = current.windSpeed,
        date = current.date.toOffsetDateTime(timezoneOffset),
        sunrise = current.sunrise.toOffsetDateTime(timezoneOffset),
        sunset = current.sunset.toOffsetDateTime(timezoneOffset)
    )
}

fun WeatherDetailsResponse.asForecast(weatherId: Int): List<ForecastWeather> {
    return daily.map {
        ForecastWeather(
            weatherId = weatherId,
            iconId = it.conditions.first().iconId,
            description = it.conditions.first().description.capitalize(Locale.getDefault()),
            minTemperature = it.temperature.min,
            maxTemperature = it.temperature.max,
            pressure = it.pressure,
            humidity = it.humidity,
            windSpeed = it.windSpeed,
            uvIndex = it.uvIndex,
            date = it.date.toOffsetDateTime(timezoneOffset),
            sunrise = it.sunrise.toOffsetDateTime(timezoneOffset),
            sunset = it.sunset.toOffsetDateTime(timezoneOffset)
        )
    }
}
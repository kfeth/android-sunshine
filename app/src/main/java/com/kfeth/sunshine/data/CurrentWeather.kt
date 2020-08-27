package com.kfeth.sunshine.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class CurrentWeather(
    @PrimaryKey val id: Int,
    val name: String,
    val iconId: String,
    val description: String,
    val temperature: Double,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    val windSpeed: Double,
    val windDegrees: Int,
    val date: Long,
    val sunrise: Long,
    val sunset: Long
) {
    constructor(response: CurrentWeatherResponse) : this(
        id = response.id,
        name = response.name,
        iconId = response.conditions.first().iconId,
        description = response.conditions.first().description,
        temperature = response.environment.temperature,
        pressure = response.environment.pressure,
        humidity = response.environment.humidity,
        visibility = response.visibility,
        windSpeed = response.wind.speed,
        windDegrees = response.wind.degrees,
        date = response.date,
        sunrise = response.system.sunrise,
        sunset = response.system.sunset,
    )
}

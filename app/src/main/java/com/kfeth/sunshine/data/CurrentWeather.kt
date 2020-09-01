package com.kfeth.sunshine.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kfeth.sunshine.utilities.toOffsetDateTime
import java.time.OffsetDateTime
import java.util.Locale

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
    val date: OffsetDateTime,
    val sunrise: OffsetDateTime,
    val sunset: OffsetDateTime
) {
    constructor(response: CurrentWeatherResponse) : this(
        id = response.id,
        name = response.name,
        iconId = response.conditions.first().iconId,
        description = response.conditions.first().description.capitalize(Locale.getDefault()),
        temperature = response.environment.temperature,
        pressure = response.environment.pressure,
        humidity = response.environment.humidity,
        visibility = response.visibility,
        windSpeed = response.wind.speed,
        windDegrees = response.wind.degrees,
        date = response.date.toOffsetDateTime(response.timezoneOffset),
        sunrise = response.system.sunrise.toOffsetDateTime(response.timezoneOffset),
        sunset = response.system.sunset.toOffsetDateTime(response.timezoneOffset)
    )
}

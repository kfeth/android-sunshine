package com.kfeth.sunshine.data.entity

import androidx.room.Entity
import java.time.OffsetDateTime

@Entity(tableName = "forecast", primaryKeys = [ "weatherId", "date" ])
data class ForecastWeather(
    val weatherId: Int,
    val iconId: String,
    val description: String,
    val minTemperature: Double,
    val maxTemperature: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val date: OffsetDateTime,
    val sunrise: OffsetDateTime,
    val sunset: OffsetDateTime
)

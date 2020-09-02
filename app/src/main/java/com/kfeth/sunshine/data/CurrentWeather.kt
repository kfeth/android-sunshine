package com.kfeth.sunshine.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "weather")
data class CurrentWeather(
    @PrimaryKey val id: Int,
    val iconId: String,
    val description: String,
    val temperature: Double,
    val pressure: Int,
    val humidity: Int,
    val uvIndex: Double,
    val visibility: Int,
    val windSpeed: Double,
    val date: OffsetDateTime,
    val sunrise: OffsetDateTime,
    val sunset: OffsetDateTime
)

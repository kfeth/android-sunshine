package com.kfeth.sunshine.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "forecast")
data class ForecastWeather(
    val weatherId: Int,
    val iconId: String,
    val description: String,
    val minTemperature: Double,
    val maxTemperature: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val uvIndex: Double,
    val date: OffsetDateTime,
    val sunrise: OffsetDateTime,
    val sunset: OffsetDateTime
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

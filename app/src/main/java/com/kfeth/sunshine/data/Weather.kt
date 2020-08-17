package com.kfeth.sunshine.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    val lat: Double,
    val lon: Double,
    val timezone: String
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

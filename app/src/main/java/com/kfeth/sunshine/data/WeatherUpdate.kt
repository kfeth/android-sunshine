package com.kfeth.sunshine.data

import androidx.room.Entity

@Entity
class WeatherUpdate(
    val id: Int,
    var temperature: Double,
    var iconId: String
)

package com.kfeth.sunshine.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(val address: String, val temperature: Long) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

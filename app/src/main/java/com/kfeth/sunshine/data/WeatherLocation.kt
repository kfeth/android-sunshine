package com.kfeth.sunshine.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class WeatherLocation(
    @PrimaryKey val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val queryString: String,
    val addressString: String,
    val countryCode: String,
) {
    constructor(query: String, location: WeatherResponse, geo: GeocodeResponse) : this(
        id = location.id,
        name = location.name,
        latitude = location.coordinates.latitude,
        longitude = location.coordinates.longitude,
        queryString = query,
        addressString = geo.addressString,
        countryCode = geo.countryCode
    )
}

package com.kfeth.sunshine.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.kfeth.sunshine.R
import com.kfeth.sunshine.utilities.degreesFormat

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val temp: String,
    val iconId: String,
    val queryString: String,
    val addressString: String,
    val countryCode: String,
) {
    constructor(query: String, location: WeatherLocationResponse, geo: GeocodeResponse) : this(
        id = location.id,
        name = location.name,
        latitude = location.coordinates.latitude,
        longitude = location.coordinates.longitude,
        temp = location.temperature.temp.degreesFormat(),
        iconId = location.conditions.first().iconId,
        queryString = query,
        addressString = geo.addressString,
        countryCode = geo.countryCode
    )

    @Ignore
    val iconResId = when(iconId) {
        // https://openweathermap.org/weather-conditions
        "01d" -> R.drawable.ic_weather_01d
        "01n" -> R.drawable.ic_weather_01n
        "02d" -> R.drawable.ic_weather_02d
        "02n" -> R.drawable.ic_weather_02n
        "03d" -> R.drawable.ic_weather_03d
        "03n" -> R.drawable.ic_weather_03n
        "04d", "04n" -> R.drawable.ic_weather_04d
        "09d" -> R.drawable.ic_weather_09d
        "09n" -> R.drawable.ic_weather_09n
        "10d" -> R.drawable.ic_weather_10d
        "10n" -> R.drawable.ic_weather_10n
        "11d" -> R.drawable.ic_weather_11d
        "11n" -> R.drawable.ic_weather_11n
        "13d" -> R.drawable.ic_weather_13d
        "13n" -> R.drawable.ic_weather_13n
        "50d" -> R.drawable.ic_weather_50d
        "50n" -> R.drawable.ic_weather_50n
        else -> R.drawable.ic_weather_03d
    }
}

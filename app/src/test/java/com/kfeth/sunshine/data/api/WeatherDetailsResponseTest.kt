package com.kfeth.sunshine.data.api

import com.kfeth.sunshine.data.TestUtils.parseResource
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

class WeatherDetailsResponseTest {

    private val response = parseResource("weatherDetails.json", WeatherDetailsResponse::class.java)

    @Test
    fun convertToCurrentWeather() {
        val weather = response.asCurrentWeather(weatherId = 101)

        assertEquals(101, weather.id)
        assertEquals("04d", weather.iconId)
        assertEquals("Broken clouds", weather.description)
        assertThat(12.34, equalTo(weather.temperature))
        assertEquals(996, weather.pressure)
        assertEquals(81, weather.humidity)
        assertEquals(10000, weather.visibility)
        assertThat(6.2, equalTo(weather.windSpeed))
        assertEquals("2020-10-05T17:46:52+01:00", weather.date.toString())
        assertEquals("2020-10-05T07:33:45+01:00", weather.sunrise.toString())
        assertEquals("2020-10-05T18:51:30+01:00", weather.sunset.toString())
    }

    @Test
    fun convertToForecast() {
        val forecast = response.asForecast(weatherId = 101)
        assertEquals(8, forecast.size)

        val last = forecast.last()

        assertEquals(101, last.weatherId)
        assertEquals("10d", last.iconId)
        assertEquals("Light rain", last.description)
        assertThat(7.09, equalTo(last.minTemperature))
        assertThat(10.37, equalTo(last.maxTemperature))
        assertEquals(1025, last.pressure)
        assertEquals(74, last.humidity)
        assertThat(3.09, equalTo(last.windSpeed))
        assertEquals("2020-10-12T13:00+01:00", last.date.toString())
        assertEquals("2020-10-12T07:46:28+01:00", last.sunrise.toString())
        assertEquals("2020-10-12T18:34:52+01:00", last.sunset.toString())
    }
}

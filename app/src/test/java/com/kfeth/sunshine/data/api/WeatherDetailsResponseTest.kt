package com.kfeth.sunshine.data.api

import com.kfeth.sunshine.TestUtils.parseResource
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class WeatherDetailsResponseTest {

    private val response = parseResource("weatherDetails.json", WeatherDetailsResponse::class.java)

    @Test
    fun convertToCurrentWeather() {
        val weather = response.asCurrentWeather(weatherId = 101)

        assertThat(weather.id, `is`(101))
        assertThat(weather.iconId, `is`("04d"))
        assertThat(weather.description, `is`("Broken clouds"))
        assertThat(weather.temperature, `is`(12.34))
        assertThat(weather.pressure, `is`(996))
        assertThat(weather.humidity, `is`(81))
        assertThat(weather.visibility, `is`(10000))
        assertThat(weather.windSpeed, `is`(6.2))
        assertThat(weather.date.toString(), `is`("2020-10-05T17:46:52+01:00"))
        assertThat(weather.sunrise.toString(), `is`("2020-10-05T07:33:45+01:00"))
        assertThat(weather.sunset.toString(), `is`("2020-10-05T18:51:30+01:00"))
    }

    @Test
    fun convertToForecast() {
        val forecast = response.asForecast(weatherId = 101)
        assertThat(forecast.size, `is`(8))

        val last = forecast.last()

        assertThat(last.weatherId, `is`(101))
        assertThat(last.iconId, `is`("10d"))
        assertThat(last.description, `is`("Light rain"))
        assertThat(last.minTemperature, `is`(7.09))
        assertThat(last.maxTemperature, `is`(10.37))
        assertThat(last.pressure, `is`(1025))
        assertThat(last.humidity, `is`(74))
        assertThat(last.windSpeed, `is`(3.09))
        assertThat(last.date.toString(), `is`("2020-10-12T13:00+01:00"))
        assertThat(last.sunrise.toString(), `is`("2020-10-12T07:46:28+01:00"))
        assertThat(last.sunset.toString(), `is`("2020-10-12T18:34:52+01:00"))
    }
}

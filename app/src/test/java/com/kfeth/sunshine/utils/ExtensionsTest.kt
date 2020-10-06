package com.kfeth.sunshine.utils

import com.kfeth.sunshine.R
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ExtensionsTest {

    @Test
    fun test_String_sanitise() {
        val query = "   FOOBar   "
        assertThat(query.sanitise(), `is`("foobar"))
    }

    @Test
    fun test_Long_toOffsetDateTime() {
        val timestamp: Long = 1601916412
        val actual = timestamp.toOffsetDateTime(offset = 3600)

        assertThat(actual.toEpochSecond(), `is`(timestamp))
        assertThat(actual.toString(), `is`("2020-10-05T17:46:52+01:00"))
    }

    @Test
    fun test_String_toWeatherIconDrawable() {
        assertThat("01d".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_01d))
        assertThat("01n".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_01n))
        assertThat("02d".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_02d))
        assertThat("02n".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_02n))
        assertThat("03d".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_03d))
        assertThat("03n".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_03n))
        assertThat("04d".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_04d))
        assertThat("04n".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_04d))
        assertThat("09d".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_09d))
        assertThat("09n".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_09n))
        assertThat("10d".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_10d))
        assertThat("10n".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_10n))
        assertThat("11d".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_11d))
        assertThat("11n".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_11n))
        assertThat("13d".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_13d))
        assertThat("13n".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_13n))
        assertThat("50d".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_50d))
        assertThat("50n".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_50n))

        assertThat("".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_03d))
        assertThat("foo".toWeatherIconDrawable(), `is`(R.drawable.ic_weather_03d))
    }
}

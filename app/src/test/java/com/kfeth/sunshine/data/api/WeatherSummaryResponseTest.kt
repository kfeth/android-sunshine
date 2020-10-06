package com.kfeth.sunshine.data.api

import com.kfeth.sunshine.data.TestUtils.parseResource
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class WeatherSummaryResponseTest {

    private val response = parseResource("weatherSummary.json", WeatherSummaryResponse::class.java)

    @Test
    fun convertToWeatherUpdates() {
        val weatherUpdates = response.asWeatherUpdate()
        assertThat(weatherUpdates.size, `is`(3))

        val last = weatherUpdates.last()
        assertThat(last.id, `is`(2643743))
        assertThat(last.temperature, `is`(14.31))
        assertThat(last.iconId, `is`("10d"))
    }

    @Test
    fun convertToWeatherLocations() {
        val locations = response.asLocations("foo")
        assertThat(locations.size, `is`(3))

        val first = locations.first()
        assertThat(first.id, `is`(2964574))
        assertThat(first.name, `is`("Dublin"))
        assertThat(first.latitude, `is`(53.34))
        assertThat(first.longitude, `is`(-6.27))
        assertThat(first.queryString, `is`("foo"))
        assertThat(first.addressString, `is`(""))
        assertThat(first.countryCode, `is`("IE"))
    }
}

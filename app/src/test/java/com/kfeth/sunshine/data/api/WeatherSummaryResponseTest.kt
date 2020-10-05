package com.kfeth.sunshine.data.api

import com.kfeth.sunshine.data.TestUtils.parseResource
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test

class WeatherSummaryResponseTest {

    private val response = parseResource("weatherSummary.json", WeatherSummaryResponse::class.java)

    @Test
    fun convertToWeatherUpdates() {
        val weatherUpdates = response.asWeatherUpdate()
        assertEquals(3, weatherUpdates.size)

        val last = weatherUpdates.last()
        assertEquals(2643743, last.id)
        assertThat(14.31, equalTo(last.temperature))
        assertEquals("10d", last.iconId)
    }

    @Test
    fun convertToWeatherLocations() {
        val locations = response.asLocations("foo")
        assertEquals(3, locations.size)

        val first = locations.first()
        assertEquals(2964574, first.id)
        assertEquals("Dublin", first.name)
        assertThat(53.34, equalTo(first.latitude))
        assertThat(-6.27, equalTo(first.longitude))
        assertEquals("foo", first.queryString)
        assertEquals("", first.addressString)
        assertEquals("IE", first.countryCode)
    }
}

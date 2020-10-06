package com.kfeth.sunshine.data.api

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class GeocodeResponseTest {

    @Test
    fun testAddress_default() {
        val response = GeocodeResponse(principalSubdivision = "Dublin", countryName = "Ireland")
        assertThat(response.address, `is`("Dublin"))
    }

    @Test
    fun testAddress_emptySubDivision() {
        val response = GeocodeResponse(principalSubdivision = "", countryName = "Ireland")
        assertThat(response.address, `is`("Ireland"))
    }
}

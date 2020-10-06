package com.kfeth.sunshine.data.api

import org.junit.Assert.assertEquals
import org.junit.Test

class GeocodeResponseTest {

    @Test
    fun testAddress_default() {
        val response = GeocodeResponse(principalSubdivision = "Dublin", countryName = "Ireland")
        assertEquals("Dublin", response.principalSubdivision)
        assertEquals("Ireland", response.countryName)
        assertEquals("Dublin", response.address)
    }

    @Test
    fun testAddress_emptySubDivision() {
        val response = GeocodeResponse(principalSubdivision = "", countryName = "Ireland")
        assertEquals("Ireland", response.address)
    }
}

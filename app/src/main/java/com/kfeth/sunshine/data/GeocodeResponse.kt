package com.kfeth.sunshine.data

import com.squareup.moshi.Json

data class GeocodeResponse(
    @Json(name = "principalSubdivision") val principalSubdivision: String,
    @Json(name = "countryName") val countryName: String
) {
    val address = if (principalSubdivision.isEmpty()) countryName else principalSubdivision
}
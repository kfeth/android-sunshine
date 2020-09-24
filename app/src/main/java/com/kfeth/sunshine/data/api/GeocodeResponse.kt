package com.kfeth.sunshine.data.api

import com.squareup.moshi.Json

class GeocodeResponse(
    @Json(name = "principalSubdivision") val principalSubdivision: String,
    @Json(name = "countryName") val countryName: String
) {
    val address = if (principalSubdivision.isEmpty()) countryName else principalSubdivision
}

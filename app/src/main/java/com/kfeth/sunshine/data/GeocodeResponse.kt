package com.kfeth.sunshine.data

import com.squareup.moshi.Json

data class GeocodeResponse(
    @Json(name = "countryCode") val countryCode: String,
    @Json(name = "localityInfo") val localityInfo: LocalityInfoResponse
)

data class LocalityInfoResponse(
    @Json(name = "administrative") val administrative: List<AdminResponse>
)

data class AdminResponse(
    @Json(name = "name") val name: String
)

val GeocodeResponse.addressString: String
    get() = when {
        localityInfo.administrative.size >= 3 -> localityInfo.administrative[2].name
        else -> localityInfo.administrative[0].name
    }
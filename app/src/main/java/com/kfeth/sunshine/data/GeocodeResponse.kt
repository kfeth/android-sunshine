package com.kfeth.sunshine.data

data class GeocodeResponse(
    val countryCode: String,
    val localityInfo: LocalityInfoResponse
)

data class LocalityInfoResponse(
    val administrative: List<AdminResponse>
)

data class AdminResponse(
    val name: String
)

val GeocodeResponse.addressString: String
    get() = when {
        localityInfo.administrative.size >= 3 -> localityInfo.administrative[2].name
        else -> localityInfo.administrative[0].name
    }
package com.kfeth.sunshine.api

import com.kfeth.sunshine.data.GeocodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GeocodeService {

    @GET
    suspend fun reverseGeocode(@Url url: String): Response<GeocodeResponse>
}

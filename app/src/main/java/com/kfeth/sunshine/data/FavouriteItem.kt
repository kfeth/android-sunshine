package com.kfeth.sunshine.data

import androidx.room.Ignore
import com.kfeth.sunshine.BuildConfig.STATIC_MAP_URL

data class FavouriteItem(
    val id: Int,
    val name: String,
    val addressString: String,
    val iconId: String,
    val description: String,
    val temperature: Double,
    val latitude: Double,
    val longitude: Double,
    val countryCode: String,
) {
    @Ignore
    val staticMapUrl = STATIC_MAP_URL.format(longitude, latitude)
}

fun List<FavouriteItem>.joinIdsToString(): String {
    return map { it.id }.joinToString(",")
}

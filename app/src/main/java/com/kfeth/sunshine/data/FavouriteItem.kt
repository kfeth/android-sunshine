package com.kfeth.sunshine.data

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
)

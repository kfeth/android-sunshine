package com.kfeth.sunshine.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey val id: Int,
    val name: String
) {
    constructor(response: LocationResponse) : this(
        id = response.id,
        name = response.name
    )
}

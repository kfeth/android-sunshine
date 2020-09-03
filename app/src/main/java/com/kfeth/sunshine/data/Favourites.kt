package com.kfeth.sunshine.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class Favourites(
    val weatherId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

package com.kfeth.sunshine.data.db

import androidx.room.TypeConverter
import java.time.OffsetDateTime

class DateConverter {

    @TypeConverter
    fun toOffsetDateTime(dateString: String): OffsetDateTime = OffsetDateTime.parse(dateString)

    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime): String = date.toString()
}

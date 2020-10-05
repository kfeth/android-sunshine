package com.kfeth.sunshine.data.db

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.OffsetDateTime
import java.time.ZoneOffset

class DateConverterTest {

    private val offsetDateTime = OffsetDateTime.of(
        2020,
        10,
        4,
        14,
        30,
        10,
        0,
        ZoneOffset.ofHours(1)
    )

    @Test
    fun toOffsetDateTime() {
        assertEquals(
            DateConverter().toOffsetDateTime("2020-10-04T14:30:10+01:00"),
            offsetDateTime
        )
    }

    @Test
    fun fromOffsetDateTime() {
        assertEquals(
            "2020-10-04T14:30:10+01:00",
            DateConverter().fromOffsetDateTime(offsetDateTime)
        )
    }
}

package com.kfeth.sunshine.data.db

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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
        assertThat(
            DateConverter().toOffsetDateTime("2020-10-04T14:30:10+01:00"),
            `is`(offsetDateTime)
        )
    }

    @Test
    fun fromOffsetDateTime() {
        assertThat(
            DateConverter().fromOffsetDateTime(offsetDateTime),
            `is`("2020-10-04T14:30:10+01:00")
        )
    }
}

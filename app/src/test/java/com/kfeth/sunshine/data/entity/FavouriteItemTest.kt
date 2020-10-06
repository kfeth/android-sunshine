package com.kfeth.sunshine.data.entity

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class FavouriteItemTest {

    private val favouriteItem = FavouriteItem(
        id = 100,
        name = "Dublin",
        addressString = "Leinster",
        iconId = "10d",
        description = "Light rain",
        temperature = 10.59,
        latitude = 53.40,
        longitude = -6.47,
        countryCode = "IE",
    )

    @Test
    fun generateStaticMapUrl() {
        assertThat(
            favouriteItem.staticMapUrl,
            startsWith("https://api.mapbox.com/styles/v1/mapbox/satellite-v9/static/-6.47,53.4,9,0/300x200@2x")
        )
    }

    @Test
    fun generateCommaSeparatedIds() {
        val favouritesList = listOf(
            favouriteItem.copy(id = 101),
            favouriteItem.copy(id = 102),
            favouriteItem.copy(id = 103),
        )
        assertThat(favouritesList.joinIdsToString(), `is`("101,102,103"))
    }
}

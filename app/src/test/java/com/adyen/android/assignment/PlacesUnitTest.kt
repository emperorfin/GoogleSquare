package com.adyen.android.assignment

import com.adyen.android.assignment.api.PlacesService
import com.adyen.android.assignment.api.VenueRecommendationsQueryBuilder
import org.junit.Assert.assertEquals
import org.junit.Test

class PlacesUnitTest {
    @Test
    fun testResponseCode() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(40.74224, -73.99386)
            .build()
        val response = PlacesService.instance
            .getVenueRecommendations(query)
            .execute()
            .body()!!
        assertEquals("Response code", 200, response.meta.code)
    }
}

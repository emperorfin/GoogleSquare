package com.adyen.android.assignment

import com.adyen.android.assignment.api.PlacesService
import com.adyen.android.assignment.api.VenueRecommendationsQueryBuilder
import org.junit.Assert.assertEquals
import org.junit.Test

class PlacesUnitTest {
    @Test
    fun testResponseCode() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.376510, 4.905890)
            .build()
        val response = PlacesService.instance
            .getVenueRecommendations(query)
            .execute()
            .body()!!
        assertEquals("Response code", 200, response.meta.code)
    }
}

package emperorfin.android.googlesquare

import emperorfin.android.googlesquare.data.datasources.remote.frameworks.retrofit.PlacesService
import emperorfin.android.googlesquare.data.datasources.remote.frameworks.retrofit.VenueRecommendationsQueryBuilder
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class PlacesUnitTest {
    @Test
    fun testResponseCode() {
        val query = VenueRecommendationsQueryBuilder()
            .setLatitudeLongitude(52.376510, 4.905890)
            .build()
        val response = PlacesService.instance
            .getVenueRecommendationsTest(query)
            .execute()

        val errorBody = response.errorBody()
        assertNull("Received an error: ${errorBody?.string()}", errorBody)

        val responseWrapper = response.body()
        assertNotNull("Response is null.", responseWrapper)
        assertEquals("Response code", 200, response.code())
    }
}

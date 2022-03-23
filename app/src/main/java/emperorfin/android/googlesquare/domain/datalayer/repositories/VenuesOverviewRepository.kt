package emperorfin.android.googlesquare.domain.datalayer.repositories

import emperorfin.android.googlesquare.domain.models.VenueModel
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent
import emperorfin.android.googlesquare.ui.events.inputs.venue.None
import emperorfin.android.googlesquare.ui.events.inputs.venue.VenueParams


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


interface VenuesOverviewRepository {

    suspend fun countVenues(): DataResultEvent<Int>

    suspend fun getVenues(paramsNothing: None, paramsSomething: VenueParams, forceUpdate: Boolean = false): DataResultEvent<List<VenueModel>>

    suspend fun saveVenue(venue: VenueModel): DataResultEvent<Long>

    suspend fun saveVenues(venues: List<VenueModel>): DataResultEvent<List<Long>>

    suspend fun deleteAllVenues(): DataResultEvent<Int>

}
package emperorfin.android.googlesquare.domain.datalayer.datasources

import emperorfin.android.googlesquare.domain.models.VenueModel
import emperorfin.android.googlesquare.domain.uilayer.events.inputs.venue.Params
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


interface VenueDataSource {

    suspend fun countVenues(): DataResultEvent<Int>

    suspend fun getVenues(params: Params): DataResultEvent<List<VenueModel>>

    suspend fun saveVenue(venue: VenueModel): DataResultEvent<Long>

    suspend fun saveVenues(venues: List<VenueModel>): DataResultEvent<List<Long>>

    suspend fun deleteAllVenues(): DataResultEvent<Int>

}
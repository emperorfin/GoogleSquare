package emperorfin.android.googlesquare.data.repositories

import emperorfin.android.googlesquare.domain.datalayer.datasources.FakeVenueDataSource
import emperorfin.android.googlesquare.domain.datalayer.repositories.FakeVenuesOverviewRepository
import emperorfin.android.googlesquare.domain.models.VenueModel
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent
import emperorfin.android.googlesquare.ui.events.inputs.venue.None
import emperorfin.android.googlesquare.ui.events.inputs.venue.VenueParams


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 14th March, 2022.
 */


class NoOpVenuesOverviewRepository(
    val venueLocalDataSource: FakeVenueDataSource,
    private val venueRemoteDataSource: FakeVenueDataSource,
) : FakeVenuesOverviewRepository {
    override suspend fun countVenues(): DataResultEvent<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getVenues(
        paramsNothing: None,
        paramsSomething: VenueParams,
        forceUpdate: Boolean
    ): DataResultEvent<List<VenueModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveVenue(venue: VenueModel): DataResultEvent<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun saveVenues(venues: List<VenueModel>): DataResultEvent<List<Long>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllVenues(): DataResultEvent<Int> {
        TODO("Not yet implemented")
    }
}
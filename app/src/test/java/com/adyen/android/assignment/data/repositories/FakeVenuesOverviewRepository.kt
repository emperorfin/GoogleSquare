package com.adyen.android.assignment.data.repositories

import com.adyen.android.assignment.domain.datalayer.datasources.VenueDataSource
import com.adyen.android.assignment.domain.datalayer.repositories.VenuesOverviewRepository
import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent
import com.adyen.android.assignment.ui.events.inputs.venue.None
import com.adyen.android.assignment.ui.events.inputs.venue.VenueParams


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 14th March, 2022.
 */


class FakeVenuesOverviewRepository(
    val venueLocalDataSource: VenueDataSource,
    private val venueRemoteDataSource: VenueDataSource,
) : VenuesOverviewRepository {
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
package com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.modelsources

import com.adyen.android.assignment.domain.datalayer.datasources.VenueDataSource
import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.uilayer.events.inputs.venue.Params
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 14th March, 2022.
 */


class FakeVenueRemoteDataSourceRetrofit : VenueDataSource {
    override suspend fun countVenues(): DataResultEvent<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getVenues(params: Params): DataResultEvent<List<VenueModel>> {
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
package com.adyen.android.assignment.domain.datalayer.datasources

import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.uilayer.events.inputs.venue.Params
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent


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
package com.adyen.android.assignment.domain.datalayer.repositories

import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.uilayer.events.inputs.venue.Params
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent
import com.adyen.android.assignment.ui.events.inputs.venue.None
import com.adyen.android.assignment.ui.events.inputs.venue.VenueParams


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
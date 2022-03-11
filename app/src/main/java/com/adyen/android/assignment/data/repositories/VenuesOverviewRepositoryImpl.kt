package com.adyen.android.assignment.data.repositories

import com.adyen.android.assignment.data.datasources.local.frameworks.room.entitysources.VenueLocalDataSourceRoom
import com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.modelsources.VenueRemoteDataSourceRetrofit
import com.adyen.android.assignment.domain.datalayer.repositories.VenuesOverviewRepository
import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent.Success
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent.Error
import com.adyen.android.assignment.domain.exceptions.VenueFailures.ListNotAvailableLocalVenueError
import com.adyen.android.assignment.domain.exceptions.VenueFailures.LocalVenueError
import com.adyen.android.assignment.domain.exceptions.VenueFailures.RemoteVenueError
import com.adyen.android.assignment.domain.exceptions.VenueFailures.RemoteGetVenueError
import com.adyen.android.assignment.domain.exceptions.VenueFailures.RepositoryGetVenueError
import com.adyen.android.assignment.ui.events.inputs.venue.None
import com.adyen.android.assignment.ui.events.inputs.venue.VenueParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlin.math.log


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


class VenuesOverviewRepositoryImpl(
    val venueLocalDataSource: VenueLocalDataSourceRoom,
    private val venueRemoteDataSource: VenueRemoteDataSourceRetrofit,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : VenuesOverviewRepository {

    private var cachedVenues: ConcurrentMap<String, VenueModel>? = null

    override suspend fun countVenues(): DataResultEvent<Int> {
        return venueLocalDataSource.countVenues()
    }

    override suspend fun getVenues(
        paramsNothing: None,
        paramsSomething: VenueParams,
        forceUpdate: Boolean
    ): DataResultEvent<List<VenueModel>> {

        return withContext(ioDispatcher) {
            // Respond immediately with cache if available and not dirty
            if (!forceUpdate) {
                cachedVenues?.let { cachedVenues ->
                    return@withContext Success(cachedVenues.values.sortedBy { it.name })
                }
            }

            val newVenues: DataResultEvent<List<VenueModel>> = fetchVenuesFromRemoteOrLocal(paramsNothing, paramsSomething, forceUpdate)

            // Refresh the cache with the new venues
            (newVenues as? Success)?.let { refreshCache(it.data) }

            cachedVenues?.values?.let { venues ->
                return@withContext Success(venues.sortedBy { it.name })
            }

            (newVenues as? Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Success(it.data)
                }
            }

//            if (newVenues is Error)
//                return@withContext Error(newVenues.failure)
//            else if (newVenues is Error && newVenues.failure is ListNotAvailableLocalVenueError)
//                return@withContext Error(newVenues.failure)
//            else if (newVenues is Error && newVenues.failure is RemoteVenueError)
//                return@withContext Error(newVenues.failure)

            //return@withContext Error((newVenues as Error).failure)
            return@withContext newVenues as Error
        }
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

    private suspend fun fetchVenuesFromRemoteOrLocal(
        paramsNothing: None, paramsSomething: VenueParams, forceUpdate: Boolean
    ): DataResultEvent<List<VenueModel>> {
        // Remote first
        val remoteVenues = venueRemoteDataSource.getVenues(paramsSomething)
        when (remoteVenues) {
            //is Error -> return remoteVenues // Timber.w("Remote data source fetch failed")
            is Success -> {
                refreshLocalDataSource(remoteVenues.data)
                return remoteVenues
            }
//            else -> throw IllegalStateException()
        }

        // Don't read from local if it's forced
        if (forceUpdate) {
            return Error(RemoteGetVenueError(cause = Exception("Can't force refresh: remote data source is unavailable.")))
        }

        // Local if remote fails
        val localVenues = venueLocalDataSource.getVenues(paramsNothing)
        if (localVenues is Success) return localVenues
//        return Error((localVenues as Error).failure)
        return Error(RepositoryGetVenueError(cause = Exception("Error fetching from remote and local.")))
    }

    private fun refreshCache(venues: List<VenueModel>) {
        cachedVenues?.clear()
        venues.sortedBy { it.name }.forEach {
            cacheAndPerform(it) {}
        }
    }

    private suspend fun refreshLocalDataSource(venues: List<VenueModel>) {
        venueLocalDataSource.deleteAllVenues()
        for (venue in venues) {
            venueLocalDataSource.saveVenue(venue)
        }
    }

    private suspend fun refreshLocalDataSource(venue: VenueModel) {
        venueLocalDataSource.saveVenue(venue)
    }

    private fun getVenueWithLatLng(LatLng: String) = cachedVenues?.get(LatLng)

    private fun cacheVenue(venue: VenueModel): VenueModel {
        val cachedVenue = VenueModel.newInstance(
            name = venue.name,
            category = venue.category,
            iconPrefix = venue.iconPrefix,
            iconSuffix = venue.iconSuffix,
            distance = venue.distance,
            latitude = venue.latitude,
            longitude = venue.longitude
        )
        // Create if it doesn't exist.
        if (cachedVenues == null) {
            cachedVenues = ConcurrentHashMap()
        }
        cachedVenues?.put("${cachedVenue.latitude},${cachedVenue.longitude}", cachedVenue)
        return cachedVenue
    }

    private inline fun cacheAndPerform(venue: VenueModel, perform: (VenueModel) -> Unit) {
        val cachedVenue = cacheVenue(venue)
        perform(cachedVenue)
    }

}
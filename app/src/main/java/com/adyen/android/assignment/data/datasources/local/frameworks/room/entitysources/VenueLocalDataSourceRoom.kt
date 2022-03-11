package com.adyen.android.assignment.data.datasources.local.frameworks.room.entitysources

import android.content.Context
import com.adyen.android.assignment.data.datasources.local.frameworks.room.dao.VenueDao
import com.adyen.android.assignment.data.datasources.local.frameworks.room.entities.mappers.VenueEntityMapper
import com.adyen.android.assignment.domain.datalayer.datasources.VenueDataSource
import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.models.mappers.VenueModelMapper
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent.Success
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent.Error
import com.adyen.android.assignment.domain.exceptions.VenueFailures.NonExistentDataLocalVenueError
import com.adyen.android.assignment.domain.exceptions.VenueFailures.ListNotAvailableLocalVenueError
import com.adyen.android.assignment.domain.exceptions.VenueFailures.LocalInsertVenueError
import com.adyen.android.assignment.domain.exceptions.VenueFailures.LocalDeleteVenueError
import com.adyen.android.assignment.domain.exceptions.VenueFailures.LocalVenueError
import com.adyen.android.assignment.domain.uilayer.events.inputs.venue.Params
import com.adyen.android.assignment.ui.events.inputs.venue.None
import com.adyen.android.assignment.ui.events.inputs.venue.VenueParams
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


class VenueLocalDataSourceRoom internal constructor(
    private val context: Context,
    private val venueDao: VenueDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val venueEntityMapper: VenueEntityMapper = VenueEntityMapper(),
    private val venueModelMapper: VenueModelMapper = VenueModelMapper()
    ) : VenueDataSource {

    companion object {

        const val POSSIBLE_TABLE_ROW_ID = -1L

    }

    override suspend fun countVenues(): DataResultEvent<Int> = withContext(ioDispatcher) {
        val numOfVenues = venueDao.countVenues()

        if (numOfVenues > 0)
            return@withContext Success(numOfVenues)
        else if (numOfVenues == 0)
            return@withContext Error(NonExistentDataLocalVenueError(Exception("No venue data available.")))

        return@withContext Error(LocalVenueError())
    }

    override suspend fun getVenues(params: Params): DataResultEvent<List<VenueModel>> = withContext(ioDispatcher) {
        when(params){
            is VenueParams -> {
                throw IllegalArgumentException("The argument passed is inappropriate.")
            }
            is None -> {
                return@withContext try {
                    val entityVenues = venueDao.getVenues()

                    if (entityVenues == null)
                        Error(LocalVenueError())
                    else if (entityVenues.isEmpty())
                        Error(ListNotAvailableLocalVenueError(cause = Exception("List of venues not available.")))

                    val modelVenues = entityVenues.map {
                        venueModelMapper.transform(it)
                    }

                    Success(modelVenues)

                }catch (e: Exception){
                    Error(LocalVenueError(cause = e))
                }
            }
            else -> throw NotImplementedError("Not yet implemented.")
        }
    }

    override suspend fun saveVenue(venue: VenueModel): DataResultEvent<Long> = withContext(ioDispatcher){
        val entityVenue = venueEntityMapper.transform(venue)

        val tableRowId: Long = venueDao.insertVenue(entityVenue)

        if (tableRowId == POSSIBLE_TABLE_ROW_ID)
            return@withContext Error(LocalInsertVenueError())

        return@withContext Success(tableRowId)
    }

    override suspend fun saveVenues(venues: List<VenueModel>): DataResultEvent<List<Long>> = withContext(ioDispatcher){
        if (venues.isEmpty())
            return@withContext Error(LocalInsertVenueError(cause = Exception("An empty list cannot be saved.")))

        val entityVenues = venues.map {
            venueEntityMapper.transform(it)
        }

        val tableRowIds: List<Long> = venueDao.insertVenues(entityVenues)

        if (tableRowIds.size != entityVenues.size)
            return@withContext Error(LocalInsertVenueError(cause = Exception("All the venues were not saved.")))

        return@withContext Success(tableRowIds)
    }

    override suspend fun deleteAllVenues(): DataResultEvent<Int> = withContext(ioDispatcher){
        val dataResultEvent: DataResultEvent<Int> = countVenues()

        val numOfVenues: Int = if(dataResultEvent is Error && dataResultEvent.failure is LocalVenueError)
            return@withContext Error(LocalDeleteVenueError(Exception("Error deleting all venues.")))
        else if(dataResultEvent is Error && dataResultEvent.failure is NonExistentDataLocalVenueError)
            0
        else
            (dataResultEvent as Success).data

        val numOfVenuesDeleted: Int = venueDao.deleteVenues()

        if (numOfVenuesDeleted != numOfVenues)
            return@withContext Error(LocalDeleteVenueError(Exception("Error deleting all venues.")))

        return@withContext Success(numOfVenuesDeleted)
    }
}
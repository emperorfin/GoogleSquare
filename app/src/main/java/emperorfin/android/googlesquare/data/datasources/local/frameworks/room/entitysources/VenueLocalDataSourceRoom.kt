package emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entitysources

import android.content.Context
import emperorfin.android.googlesquare.R
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.dao.VenueDao
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entities.mappers.VenueEntityMapper
import emperorfin.android.googlesquare.domain.datalayer.datasources.VenueDataSource
import emperorfin.android.googlesquare.domain.models.VenueModel
import emperorfin.android.googlesquare.domain.models.mappers.VenueModelMapper
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent.Success
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent.Error
import emperorfin.android.googlesquare.domain.exceptions.VenueFailures.NonExistentDataLocalVenueError
import emperorfin.android.googlesquare.domain.exceptions.VenueFailures.ListNotAvailableLocalVenueError
import emperorfin.android.googlesquare.domain.exceptions.VenueFailures.LocalInsertVenueError
import emperorfin.android.googlesquare.domain.exceptions.VenueFailures.LocalDeleteVenueError
import emperorfin.android.googlesquare.domain.exceptions.VenueFailures.LocalVenueError
import emperorfin.android.googlesquare.domain.uilayer.events.inputs.venue.Params
import emperorfin.android.googlesquare.ui.events.inputs.venue.None
import emperorfin.android.googlesquare.ui.events.inputs.venue.VenueParams
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
            return@withContext Error(
                NonExistentDataLocalVenueError(
                    Exception(context.getString(R.string.error_venue_data_unavailable))
                )
            )

        return@withContext Error(LocalVenueError())
    }

    override suspend fun getVenues(
        params: Params
    ): DataResultEvent<List<VenueModel>> = withContext(ioDispatcher) {
        when(params){
            is VenueParams -> {
                throw IllegalArgumentException(
                    context.getString(R.string.error_inappropriate_argument_passed)
                )
            }
            is None -> {
                return@withContext try {
                    val entityVenues = venueDao.getVenues()

                    if (entityVenues == null)
                        Error(LocalVenueError())
                    else if (entityVenues.isEmpty())
                        Error(
                            ListNotAvailableLocalVenueError(
                                cause = Exception(
                                    context.getString(R.string.error_venues_unavailable)
                                )
                            )
                        )

                    val modelVenues = entityVenues.map {
                        venueModelMapper.transform(it)
                    }

                    Success(modelVenues)

                }catch (e: Exception){
                    Error(LocalVenueError(cause = e))
                }
            }
            else -> throw NotImplementedError(context.getString(R.string.error_not_yet_implemented))
        }
    }

    override suspend fun saveVenue(
        venue: VenueModel
    ): DataResultEvent<Long> = withContext(ioDispatcher){
        val entityVenue = venueEntityMapper.transform(venue)

        val tableRowId: Long = venueDao.insertVenue(entityVenue)

        if (tableRowId == POSSIBLE_TABLE_ROW_ID)
            return@withContext Error(LocalInsertVenueError())

        return@withContext Success(tableRowId)
    }

    override suspend fun saveVenues(
        venues: List<VenueModel>
    ): DataResultEvent<List<Long>> = withContext(ioDispatcher){
        if (venues.isEmpty())
            return@withContext Error(
                LocalInsertVenueError(
                    cause = Exception(context.getString(R.string.error_cant_save_empty_list))
                )
            )

        val entityVenues = venues.map {
            venueEntityMapper.transform(it)
        }

        val tableRowIds: List<Long> = venueDao.insertVenues(entityVenues)

        if (tableRowIds.size != entityVenues.size)
            return@withContext Error(
                LocalInsertVenueError(
                    cause = Exception(context.getString(R.string.error_all_venues_not_saved))
                )
            )

        return@withContext Success(tableRowIds)
    }

    override suspend fun deleteAllVenues(): DataResultEvent<Int> =
        withContext(ioDispatcher){
        val dataResultEvent: DataResultEvent<Int> = countVenues()

        val numOfVenues: Int = if(dataResultEvent is Error && dataResultEvent.failure is LocalVenueError)
            return@withContext Error(
                LocalDeleteVenueError(
                    Exception(context.getString(R.string.error_deleting_all_venues))
                )
            )
        else if(dataResultEvent is Error &&
            dataResultEvent.failure is NonExistentDataLocalVenueError) {
            0
        } else {
            (dataResultEvent as Success).data
        }

        val numOfVenuesDeleted: Int = venueDao.deleteVenues()

        if (numOfVenuesDeleted != numOfVenues)
            return@withContext Error(
                LocalDeleteVenueError(
                    Exception(context.getString(R.string.error_deleting_all_venues))
                )
            )

        return@withContext Success(numOfVenuesDeleted)
    }
}
package emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entitysources

import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entities.VenueEntity
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entities.mappers.VenueEntityMapper
import emperorfin.android.googlesquare.domain.datalayer.datasources.FakeVenueDataSource
import emperorfin.android.googlesquare.domain.models.VenueModel
import emperorfin.android.googlesquare.domain.models.mappers.VenueModelMapper
import emperorfin.android.googlesquare.domain.uilayer.events.inputs.venue.Params
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent.Success
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent.Error
import emperorfin.android.googlesquare.domain.exceptions.VenueFailures.ListNotAvailableLocalVenueError
import emperorfin.android.googlesquare.domain.exceptions.VenueFailures.LocalInsertVenueError
import emperorfin.android.googlesquare.domain.exceptions.VenueFailures.LocalVenueError
import emperorfin.android.googlesquare.ui.events.inputs.venue.None
import emperorfin.android.googlesquare.ui.events.inputs.venue.VenueParams


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 14th March, 2022.
 */


class FakeVenueLocalDataSourceRoom internal constructor(
    private var entityVenues: MutableList<VenueEntity>? = mutableListOf(),
    private val venueEntityMapper: VenueEntityMapper = VenueEntityMapper(),
    private val venueModelMapper: VenueModelMapper = VenueModelMapper()
) : FakeVenueDataSource {
    override suspend fun countVenues(): DataResultEvent<Int> {
        val countVenues: Int = entityVenues?.size ?: return Error(LocalVenueError())

        return Success(countVenues)
    }

    override suspend fun getVenues(params: Params): DataResultEvent<List<VenueModel>> {
        when(params){
            is VenueParams -> {
                throw IllegalArgumentException("The argument passed is inappropriate.")
            }
            is None -> {
                entityVenues?.let { venues ->
                    val modelVenues = venues.map {
                        venueModelMapper.transform(it)
                    }

                    return if (venues.isNotEmpty()) {
                        Success(modelVenues)
                    } else {
                        Error(ListNotAvailableLocalVenueError())
                    }
                }

                return Error(ListNotAvailableLocalVenueError(cause = Exception("List of venues not available.")))
            }
            else -> throw NotImplementedError("Not yet implemented.")
        }
    }

    override suspend fun saveVenue(venue: VenueModel): DataResultEvent<Long> {
        val entityVenue = venueEntityMapper.transform(venue)

        val hasInserted: Boolean? = entityVenues?.add(entityVenue)

        if (hasInserted != null && hasInserted) {
            val arrayElementIndex: Int? = entityVenues?.size?.minus(1)

            arrayElementIndex?.let { return@let Success(it) }
        }

        return Error(LocalInsertVenueError())
    }

    override suspend fun saveVenues(venues: List<VenueModel>): DataResultEvent<List<Long>> {
        val initialArraySize: Int = entityVenues?.size ?: return Error(LocalVenueError())

        if (venues.isEmpty())
            return Error(LocalInsertVenueError(cause = Exception("An empty list cannot be saved.")))

        val entityVenueList = venues.map {
            venueEntityMapper.transform(it)
        }

        entityVenues?.addAll(entityVenueList)

        val currentArraySize: Int? = entityVenues?.size

        currentArraySize?.let {
            return@let if (it > initialArraySize) {
                val arrayInsertionSize: Int = it - initialArraySize

                Success(arrayInsertionSize)
            } else {
                Error(LocalVenueError())
            }
        }

        return Error(LocalVenueError())

    }

    override suspend fun deleteAllVenues(): DataResultEvent<Int> {
        val deletedArrayElementSize: Int = entityVenues?.size ?: return Error(LocalVenueError())

        entityVenues?.clear() ?: return Error(LocalVenueError())

        return Success(deletedArrayElementSize)
    }
}
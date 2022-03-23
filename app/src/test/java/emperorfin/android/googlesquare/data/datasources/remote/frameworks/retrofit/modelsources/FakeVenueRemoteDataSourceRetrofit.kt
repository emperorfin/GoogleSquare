package emperorfin.android.googlesquare.data.datasources.remote.frameworks.retrofit.modelsources

import emperorfin.android.googlesquare.data.datasources.remote.frameworks.retrofit.models.venue.VenueDataTransferObject
import emperorfin.android.googlesquare.domain.datalayer.datasources.FakeVenueDataSource
import emperorfin.android.googlesquare.domain.models.VenueModel
import emperorfin.android.googlesquare.domain.uilayer.events.inputs.venue.Params
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent
import emperorfin.android.googlesquare.domain.models.mappers.VenueModelMapper
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent.Success
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent.Error
import emperorfin.android.googlesquare.domain.exceptions.VenueFailures.ListNotAvailableRemoteVenueError
import emperorfin.android.googlesquare.ui.events.inputs.venue.None
import emperorfin.android.googlesquare.ui.events.inputs.venue.VenueParams


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 14th March, 2022.
 */


class FakeVenueRemoteDataSourceRetrofit internal constructor(
    private var dataTransferObjectVenues: MutableList<VenueDataTransferObject>? = mutableListOf(),
    private val venueModelMapper: VenueModelMapper = VenueModelMapper()

    ) : FakeVenueDataSource {
    override suspend fun countVenues(): DataResultEvent<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getVenues(params: Params): DataResultEvent<List<VenueModel>> {
        when(params){
            is VenueParams -> {
                dataTransferObjectVenues?.let { venues ->
                    val modelVenues = venues.map {
                        venueModelMapper.transform(it)
                    }

                    return if (venues.isNotEmpty()) {
                        Success(modelVenues)
                    } else {
                        Error(ListNotAvailableRemoteVenueError())
                    }
                }

                return Error(ListNotAvailableRemoteVenueError(cause = Exception("List of venues not available.")))
            }
            is None -> {
                throw IllegalArgumentException("The argument passed is inappropriate.")
            }
            else -> throw NotImplementedError("Not yet implemented.")
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
}
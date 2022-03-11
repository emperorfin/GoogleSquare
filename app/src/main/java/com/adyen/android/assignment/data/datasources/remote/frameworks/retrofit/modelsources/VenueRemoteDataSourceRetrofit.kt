package com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.modelsources

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.PlacesService
import com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.VenueRecommendationsQueryBuilder
import com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.models.venue.ResponseWrapper
import com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.models.venue.VenueDataTransferObject
import com.adyen.android.assignment.domain.datalayer.datasources.VenueDataSource
import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.models.mappers.VenueModelMapper
import com.adyen.android.assignment.domain.uilayer.events.inputs.venue.Params
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent.Success
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent.Error
import com.adyen.android.assignment.ui.events.inputs.venue.None
import com.adyen.android.assignment.ui.events.inputs.venue.VenueParams
import com.adyen.android.assignment.domain.exceptions.VenueFailures.RemoteVenueError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


class VenueRemoteDataSourceRetrofit internal constructor(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val venueModelMapper: VenueModelMapper = VenueModelMapper()
): VenueDataSource {

    override suspend fun countVenues(): DataResultEvent<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getVenues(params: Params): DataResultEvent<List<VenueModel>> = withContext(ioDispatcher) {
        when(params){
            is VenueParams -> {
                if (params.latitude == null || params.longitude == null)
                    throw IllegalArgumentException("The latitude and longitude must not be null.")

                val currentLatitude: Double = params.latitude
                val currentLongitude: Double = params.longitude

                val query = VenueRecommendationsQueryBuilder()
                    .setLatitudeLongitude(latitude = currentLatitude, longitude = currentLongitude)
                    .build()

                //test
//                val dataResultEventLiveData = MutableLiveData<DataResultEvent<List<VenueModel>>>()
//                lateinit var dataResultEventVenueModelList: DataResultEvent<List<VenueModel>>
//
//                PlacesService.instance.getVenueRecommendations(query).enqueue(object : Callback<ResponseWrapper> {
//                    override fun onResponse(
//                        call: Call<ResponseWrapper>,
//                        response: Response<ResponseWrapper>
//                    ) {
//                        if (response.isSuccessful && response.body() != null) {
//                            response.body()?.let {
//                                val modelVenues: List<VenueModel> = buildVenueModelList(it.venues!!)
//
////                                dataResultEventVenueModelList = Success(modelVenues)
//                                dataResultEventLiveData.postValue(Success(modelVenues))
//                            }
//                        } else {
//                            onFailure(call, Throwable("Unsuccessful request for venues: " + response.code()))
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ResponseWrapper>, t: Throwable) {
////                        dataResultEventVenueModelList = Error(RemoteVenueError(cause = t))
//                        dataResultEventLiveData.postValue(Error(RemoteVenueError(cause = t)))
//                    }
//                })
//                withContext(Dispatchers.Main){
//                    dataResultEventLiveData.observeForever {
//                        dataResultEventVenueModelList = it
//                    }
//                }
//                return@withContext dataResultEventVenueModelList

//                val response = PlacesService.instance.getVenueRecommendations(query).execute()
//                if (response.isSuccessful && response.body() != null){
//                    response.body()?.let {
//                        it.venues?.let {venues ->
//                            val modelVenues: List<VenueModel> = buildVenueModelList(venues)
//                            return@withContext Success(modelVenues)
//                        }
//                    }
//                }
//                return@withContext Error(RemoteVenueError(Exception("Unsuccessful request for venues: " + response.code())))


//                val response = PlacesService.instance.getVenueRecommendations(query)
//                lateinit var dataResultEventVenueModelList: DataResultEvent<List<VenueModel>>
//                withContext(Dispatchers.Main){
//                    if (response.isSuccessful && response.body() != null){
//                        response.body()?.let {
//                            it.venues?.let {venues ->
//                                val modelVenues: List<VenueModel> = buildVenueModelList(venues)
//                                return@withContext Success(modelVenues)
//                            }
//                        }
//                    }
//                    return@withContext Error(RemoteVenueError(Exception("Unsuccessful request for venues: " + response.code())))
//                }
//                return@withContext Error(RemoteVenueError(Exception("Unsuccessful request for venues: " + response.code())))

//                val response = PlacesService.instance.getVenueRecommendations(query)
//                lateinit var dataResultEventVenueModelList: DataResultEvent<List<VenueModel>>
//                withContext(Dispatchers.Main){
//                    if (response.isSuccessful){ //  && response.body() != null
//                        response.body()?.let {
//                            it.venues?.let {venues ->
//                                val modelVenues: List<VenueModel> = buildVenueModelList(venues)
//                                return@withContext Success(modelVenues)
//                            }
//                        }
//                    }
//                    return@withContext Error(RemoteVenueError(Exception("Unsuccessful request for venues: " + response.code())))
//                }
//                return@withContext Error(RemoteVenueError(Exception("Unsuccessful request for venues: " + response.code())))

                val response = PlacesService.instance.getVenueRecommendations(query)
                lateinit var dataResultEventVenueModelList: DataResultEvent<List<VenueModel>>
                withContext(Dispatchers.Main){
                    if (response.isSuccessful){ //  && response.body() != null
                        response.body()?.let {

                            Toast.makeText(context, "${it.results?.size}", Toast.LENGTH_LONG).show()
                            Toast.makeText(context, "it: ${it.results}", Toast.LENGTH_LONG).show()

                            it.results?.let {venues ->
                                val modelVenues: List<VenueModel> = buildVenueModelList(venues)
//                                return@withContext Success(modelVenues)
                                Toast.makeText(context, "(1)", Toast.LENGTH_LONG).show()
                                return@withContext Success(modelVenues)
                            }
                        }
                    }
                    Toast.makeText(context, "(2)", Toast.LENGTH_LONG).show()
                    return@withContext Error(RemoteVenueError(Exception("Unsuccessful request for venues: " + response.code())))
                }
//                Toast.makeText(context, "(3)", Toast.LENGTH_LONG).show()
//                return@withContext Error(RemoteVenueError(Exception("Unsuccessful request for venues: " + response.code())))


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

    private fun buildVenueModelList(dataTransferObjectVenues: List<VenueDataTransferObject>): List<VenueModel> {
        return dataTransferObjectVenues.map {
            venueModelMapper.transform(it)
        }
    }
}
package com.adyen.android.assignment.ui.screens.venuesoverview.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.data.datasources.local.frameworks.room.AppRoomDatabase
import com.adyen.android.assignment.data.datasources.local.frameworks.room.entitysources.VenueLocalDataSourceRoom
import com.adyen.android.assignment.data.datasources.sharedpreferences.SharedPreferencesUtilImpl
import com.adyen.android.assignment.data.repositories.VenuesOverviewRepositoryImpl
import com.adyen.android.assignment.domain.datalayer.repositories.VenuesOverviewRepository
import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.models.mappers.VenueModelMapper
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent.Success
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent.Error
import com.adyen.android.assignment.domain.exceptions.VenueFailures.ListNotAvailableLocalVenueError
import com.adyen.android.assignment.domain.exceptions.VenueFailures.LocalVenueError
import com.adyen.android.assignment.domain.uilayer.events.outputs.succeeded
import com.adyen.android.assignment.ui.events.inputs.venue.None
import com.adyen.android.assignment.ui.events.inputs.venue.VenueParams
import com.adyen.android.assignment.ui.screens.venuesoverview.enums.VenuesRequestStatus
import com.adyen.android.assignment.ui.uimodels.VenueUiModel
import com.adyen.android.assignment.ui.uimodels.mappers.VenueUiModelMapper
import com.adyen.android.assignment.ui.utils.VenuesDataGeneratorUtil
import com.adyen.android.assignment.ui.utils.InternetConnectivityUtil.hasInternetConnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


class VenuesOverviewViewModel(
    application: Application,
//    private val venuesOverviewRepository: VenuesOverviewRepositoryImpl
    private val venuesOverviewRepository: VenuesOverviewRepository
) : AndroidViewModel(application) {

    companion object {

        const val ERROR_CODE_NO_INTERNET_CONNECTION = "ERROR_CODE_NO_INTERNET_CONNECTION"

    }

    private val applicationContext = getApplication<Application>()

    private lateinit var sharedPreferencesUtil: SharedPreferencesUtilImpl

    var isFirstRun = true

    private val _venuesRequestStatus = MutableLiveData<VenuesRequestStatus>()
    val venuesRequestStatus: LiveData<VenuesRequestStatus>
        get() = _venuesRequestStatus

    private val _venues = MutableLiveData<List<VenueUiModel>>()
    val venues: LiveData<List<VenueUiModel>>
        get() = _venues

    private val _noInternetConnectionError = MutableLiveData<String>()
    val noInternetConnectionError: LiveData<String>
        get() = _noInternetConnectionError

    init {
        sharedPreferencesUtil = SharedPreferencesUtilImpl(applicationContext)

        // Option 1 of 4 (SAMPLE DATA)
//        generateVenuesSampleData()
        // Option 2 of 4 (SAMPLE DATA)
//        getDatabaseVenuesSampleDataWithoutLocalDataSource()
        // Option 3 of 4 (SAMPLE DATA)
//        getDatabaseVenuesSampleDataViaLocalDataSource()
        // Option 4 of 4 (LIVE DATA) - see loadVenues(...)
    }

    fun emitNoInternetConnectionError(value: String?){
        _noInternetConnectionError.postValue(value)
    }

    private fun generateVenuesSampleData() = viewModelScope.launch {
        _venuesRequestStatus.value = VenuesRequestStatus.LOADING

        delay(2000)

        _venues.value = VenuesDataGeneratorUtil.getVenues()

//        _venues.value = listOf()

        if(_venues.value?.isEmpty() == true)
            _venuesRequestStatus.value = VenuesRequestStatus.NO_DATA
        else
            _venuesRequestStatus.value = VenuesRequestStatus.DONE
    }

    private fun getDatabaseVenuesSampleDataWithoutLocalDataSource() = viewModelScope.launch{
        _venuesRequestStatus.value = VenuesRequestStatus.LOADING

        val entityVenues = AppRoomDatabase.getInstance(applicationContext).mVenueDao.getVenues()
        val venueModelMapper = VenueModelMapper()
        val venueUiModelMapper = VenueUiModelMapper()
        _venues.value = entityVenues.map {
            venueModelMapper.transform(it)
        }.map {
            venueUiModelMapper.transform(it)
        }

        if(_venues.value?.isEmpty() == true)
            _venuesRequestStatus.value = VenuesRequestStatus.NO_DATA
        else
            _venuesRequestStatus.value = VenuesRequestStatus.DONE
    }

    private fun getDatabaseVenuesSampleDataViaLocalDataSource() = viewModelScope.launch{
        _venuesRequestStatus.value = VenuesRequestStatus.LOADING

        val localDataSourceRoom: VenueLocalDataSourceRoom = VenueLocalDataSourceRoom(
            applicationContext,
            AppRoomDatabase.getInstance(applicationContext).mVenueDao
        )

        val params = None()
        val dataResultEvent: DataResultEvent<List<VenueModel>> = localDataSourceRoom.getVenues(params)

        if (dataResultEvent is Error && (dataResultEvent.failure is LocalVenueError || dataResultEvent.failure is ListNotAvailableLocalVenueError)){
            _venuesRequestStatus.value = VenuesRequestStatus.NO_DATA
        }else if (dataResultEvent.succeeded){
            val modelVenues = (dataResultEvent as Success).data

            val venueUiModelMapper = VenueUiModelMapper()

            _venues.value = modelVenues.map {
                venueUiModelMapper.transform(it)
            }

            _venuesRequestStatus.value = VenuesRequestStatus.DONE
        }
    }

    private fun getDatabaseVenuesRealDataViaRepository(paramsNothing: None, paramsSomething: VenueParams, forceUpdate: Boolean) = viewModelScope.launch{
        _venuesRequestStatus.value = VenuesRequestStatus.LOADING

        val venuesDataResultEvent = venuesOverviewRepository.getVenues(paramsNothing, paramsSomething, forceUpdate)

        if (venuesDataResultEvent is Success){
            val modelVenues: List<VenueModel> = venuesDataResultEvent.data

            val venueUiModelMapper = VenueUiModelMapper()

            _venues.value = modelVenues.map {
                venueUiModelMapper.transform(it)
            }

            _venuesRequestStatus.value = VenuesRequestStatus.DONE
        }else{
            _venuesRequestStatus.value = VenuesRequestStatus.NO_DATA

//            _venues.value = emptyList()
        }
    }

    private fun getSavedVenues(){
        getDatabaseVenuesSampleDataViaLocalDataSource()
    }

    fun loadVenues(paramsNothing: None, paramsSomething: VenueParams){

//        if (!sharedPreferencesUtil.getVenueOverviewScreenFirstRun()){
//            sharedPreferencesUtil.setVenueOverviewScreenFirstRun(false)
//
//            if (hasInternetConnection(applicationContext)){
//                getDatabaseVenuesRealDataViaRepository(
//                    paramsNothing = paramsNothing,
//                    paramsSomething = paramsSomething,
//                    false
//                )
//            } else {
//                Toast.makeText(
//                    applicationContext,
//                    "No internet connection. Loading saved venues...",
//                    Toast.LENGTH_LONG
//                ).show()
//
//                getSavedVenues()
//            }
//        } else {
//            if (hasInternetConnection(applicationContext)){
//                getDatabaseVenuesRealDataViaRepository(
//                    paramsNothing = paramsNothing,
//                    paramsSomething = paramsSomething,
//                    true
//                )
//            } else {
////                Toast.makeText(
////                    applicationContext,
////                    "No internet connection. Try again.",
////                    Toast.LENGTH_LONG
////                ).show()
//
//                _noInternetConnectionError.postValue(ERROR_CODE_NO_INTERNET_CONNECTION)
//            }
//        }

        //-----------------------------

//        var venuesCount by Delegates.notNull<Int>()
//
//        viewModelScope.launch {
//            val venuesCountDataResultEvent = venuesOverviewRepository.countVenues()
//
//            venuesCount = if (venuesCountDataResultEvent.succeeded)
//                (venuesCountDataResultEvent as Success).data
//            else
//                -1
//        }
//
//        if (venuesCount > 0){
//            if (hasInternetConnection(applicationContext)){
//                getDatabaseVenuesRealDataViaRepository(
//                    paramsNothing = paramsNothing,
//                    paramsSomething = paramsSomething,
//                    false
//                )
//            } else {
//                Toast.makeText(
//                    applicationContext,
//                    "No internet connection. Loading saved venues...",
//                    Toast.LENGTH_LONG
//                ).show()
//
//                getSavedVenues()
//            }
//        } else {
//            if (hasInternetConnection(applicationContext)){
//                getDatabaseVenuesRealDataViaRepository(
//                    paramsNothing = paramsNothing,
//                    paramsSomething = paramsSomething,
//                    true
//                )
//            } else {
//                _noInternetConnectionError.postValue(ERROR_CODE_NO_INTERNET_CONNECTION)
//            }
//        }

        //---------------------------

        viewModelScope.launch {
            var venuesCount by Delegates.notNull<Int>()

            val venuesCountDataResultEvent = venuesOverviewRepository.countVenues()

            venuesCount = if (venuesCountDataResultEvent.succeeded)
                (venuesCountDataResultEvent as Success).data
            else
                -1

            Toast.makeText(
                applicationContext,
                "venuesCount: 1 $venuesCount",
                Toast.LENGTH_LONG
            ).show()

            if (venuesCount > 0){
                if (hasInternetConnection(applicationContext)){
                    getDatabaseVenuesRealDataViaRepository(
                        paramsNothing = paramsNothing,
                        paramsSomething = paramsSomething,
                        false
                    )
                } else {
                    Toast.makeText(
                        applicationContext,
                        "No internet connection. Loading saved venues...",
                        Toast.LENGTH_LONG
                    ).show()

                    getSavedVenues()
                }
            } else {
                if (hasInternetConnection(applicationContext)){
                    getDatabaseVenuesRealDataViaRepository(
                        paramsNothing = paramsNothing,
                        paramsSomething = paramsSomething,
                        true
                    )
                } else {
                    _noInternetConnectionError.postValue(ERROR_CODE_NO_INTERNET_CONNECTION)
                }
            }
        }
    }

}
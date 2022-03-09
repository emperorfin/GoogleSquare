package com.adyen.android.assignment.ui.screens.venuesoverview.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.data.datasources.local.frameworks.room.AppRoomDatabase
import com.adyen.android.assignment.data.datasources.local.frameworks.room.entitysources.VenueLocalDataSourceRoom
import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.models.mappers.VenueModelMapper
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent.Success
import com.adyen.android.assignment.domain.uilayer.events.outputs.DataResultEvent.Error
import com.adyen.android.assignment.domain.exceptions.VenueFailures.ListNotAvailableLocalVenueError
import com.adyen.android.assignment.domain.exceptions.VenueFailures.LocalVenueError
import com.adyen.android.assignment.domain.uilayer.events.outputs.succeeded
import com.adyen.android.assignment.ui.screens.venuesoverview.enums.VenuesRequestStatus
import com.adyen.android.assignment.ui.uimodels.VenueUiModel
import com.adyen.android.assignment.ui.uimodels.mappers.VenueUiModelMapper
import com.adyen.android.assignment.ui.utils.VenuesDataGeneratorUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


class VenuesOverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext = getApplication<Application>()

    private val _venuesRequestStatus = MutableLiveData<VenuesRequestStatus>()
    val venuesRequestStatus: LiveData<VenuesRequestStatus>
        get() = _venuesRequestStatus

    private val _venues = MutableLiveData<List<VenueUiModel>>()
    val venues: LiveData<List<VenueUiModel>>
        get() = _venues

    init {
        // Option 1 of 4 (SAMPLE DATA)
//        generateVenuesSampleData()
        // Option 2 of 4 (SAMPLE DATA)
//        getDatabaseVenuesSampleDataWithoutLocalDataSource()
        // Option 3 of 4 (SAMPLE DATA)
        getDatabaseVenuesSampleDataViaLocalDataSource()
        // Option 4 of 4 (LIVE DATA)
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

        val dataResultEvent: DataResultEvent<List<VenueModel>> = localDataSourceRoom.getVenues()

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

}
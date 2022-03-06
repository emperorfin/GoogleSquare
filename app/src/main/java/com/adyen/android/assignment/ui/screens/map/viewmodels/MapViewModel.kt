package com.adyen.android.assignment.ui.screens.map.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 05th March, 2022.
 */


class MapViewModel : ViewModel() {

    companion object {
        const val ERROR_CODE_NO_CURRENT_LOCATION = 501
    }

    var isFirstRun = true

    private val _currentLocationLiveData = MutableLiveData<Location>()
    val currentLocationLiveData: LiveData<Location>
        get() = _currentLocationLiveData

    private val _currentLocationResultsError = MutableLiveData<Int>()
    val currentLocationResultsError: LiveData<Int>
        get() = _currentLocationResultsError

    fun refreshCurrentLocation(currentLocation: Location?) {
        if (currentLocation == null) {
            _currentLocationResultsError.postValue(ERROR_CODE_NO_CURRENT_LOCATION)
            return
        }

        _currentLocationLiveData.value = currentLocation
    }

}
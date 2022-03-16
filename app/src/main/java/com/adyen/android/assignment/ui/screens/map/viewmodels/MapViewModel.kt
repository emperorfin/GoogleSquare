package com.adyen.android.assignment.ui.screens.map.viewmodels

import android.app.Application
import android.location.Location
import androidx.lifecycle.*
import com.adyen.android.assignment.ui.utils.InternetConnectivityUtil.hasInternetConnection


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 05th March, 2022.
 */


class MapViewModel(application: Application) : AndroidViewModel(application) {

    companion object {

        const val ERROR_CODE_NO_CURRENT_LOCATION = 501

        const val ERROR_CODE_NO_INTERNET_CONNECTION = "ERROR_CODE_NO_INTERNET_CONNECTION"

    }

    private val applicationContext = getApplication<Application>()

    var isFirstRun = true

    private val _currentLocationLiveData = MutableLiveData<Location>()
    val currentLocationLiveData: LiveData<Location>
        get() = _currentLocationLiveData

    private val _currentLocationResultsError = MutableLiveData<Int>()
    val currentLocationResultsError: LiveData<Int>
        get() = _currentLocationResultsError

    private val _noInternetConnectionError = MutableLiveData<String>()
    val noInternetConnectionError: LiveData<String>
        get() = _noInternetConnectionError

    fun emitCurrentLocationResultsError(value: Int?){
        _currentLocationResultsError.postValue(value)
    }

    fun emitNoInternetConnectionError(value: String?){
        _noInternetConnectionError.postValue(value)
    }

    fun refreshCurrentLocation(currentLocation: Location?) {
        if (currentLocation == null) {
            _currentLocationResultsError.postValue(ERROR_CODE_NO_CURRENT_LOCATION)
            return
        }

        _currentLocationLiveData.postValue(currentLocation)
    }

}
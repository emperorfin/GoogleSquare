package com.adyen.android.assignment.ui.screens.venuesoverview.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.ui.screens.venuesoverview.enums.VenuesRequestStatus
import com.adyen.android.assignment.ui.uimodels.VenueUiModel
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
        generateVenuesSampleData()
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

}
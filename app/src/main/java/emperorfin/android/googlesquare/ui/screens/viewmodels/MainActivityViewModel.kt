package emperorfin.android.googlesquare.ui.screens.viewmodels

import android.app.Application
import android.location.Location
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import emperorfin.android.googlesquare.ui.events.outputs.EventDataImpl


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 04th March, 2022.
 */


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val applicationContext = getApplication<Application>()

    private val _currentLocationLiveData = MutableLiveData<Location>()
    val currentLocationLiveData: LiveData<Location>
        get() = _currentLocationLiveData

    private val _openMapScreenEvent = MutableLiveData<EventDataImpl<View?>>()
    val openMapScreenEvent: LiveData<EventDataImpl<View?>>
        get() = _openMapScreenEvent

    private val _openVenuesScreenEvent = MutableLiveData<EventDataImpl<View?>>()
    val openVenuesScreenEvent: LiveData<EventDataImpl<View?>>
        get() = _openVenuesScreenEvent

    fun emitCurrentLocationLiveData(currentLocation: Location?){
        _currentLocationLiveData.postValue(currentLocation)
    }

    // Return type just for the purpose of testing.
    fun showComingSoonMessageToast(@StringRes message: Int): Int{
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()

        return message
    }

    fun openMapScreen(view: View?){
        _openMapScreenEvent.postValue(EventDataImpl(view))
    }

    fun openVenuesScreen(view: View?){
        _openVenuesScreenEvent.postValue(EventDataImpl(view))
    }
}
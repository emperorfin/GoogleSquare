package emperorfin.android.googlesquare.ui.screens.map.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 11th March, 2022.
 */


class MapViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MapViewModel::class.java)){
            return MapViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
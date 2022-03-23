package emperorfin.android.googlesquare.ui.screens.map.contracts

import android.location.Location


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Friday 11th March, 2022.
 */


interface IMapFragmentCallbacks {

    fun onCurrentLocationReady(currentLocation: Location)

}
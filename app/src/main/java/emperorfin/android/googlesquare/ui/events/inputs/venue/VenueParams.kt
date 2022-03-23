package emperorfin.android.googlesquare.ui.events.inputs.venue

import emperorfin.android.googlesquare.domain.uilayer.events.inputs.venue.Params


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Wednesday 09th March, 2022.
 */


data class VenueParams(
    val name: String? = null,
    val category: String? = null,
    val iconPrefix: String? = null,
    val iconSuffix: String? = null,
    val distance: Int? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
) : Params

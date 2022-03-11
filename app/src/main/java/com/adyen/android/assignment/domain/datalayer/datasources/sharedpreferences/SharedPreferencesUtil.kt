package com.adyen.android.assignment.domain.datalayer.datasources.sharedpreferences


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 10th March, 2022.
 */


interface SharedPreferencesUtil {

    fun setVenueOverviewScreenFirstRun(value: Boolean)

    fun getVenueOverviewScreenFirstRun(): Boolean

}
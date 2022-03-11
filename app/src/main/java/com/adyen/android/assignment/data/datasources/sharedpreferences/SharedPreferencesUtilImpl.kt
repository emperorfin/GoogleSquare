package com.adyen.android.assignment.data.datasources.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.adyen.android.assignment.domain.datalayer.datasources.sharedpreferences.SharedPreferencesUtil


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Thursday 10th March, 2022.
 */


class SharedPreferencesUtilImpl(val context: Context) : SharedPreferencesUtil {

    companion object {

        private var PREF_KEY_IS_VENUE_OVERVIEW_SCREEN_FIRST_RUN = SharedPreferencesUtilImpl::class.qualifiedName + ".PREF_KEY_IS_VENUE_OVERVIEW_SCREEN_FIRST_RUN"

        private const val DESIRED_SHARED_PREFERENCES_FILE = "shared"

    }

    private lateinit var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(DESIRED_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)
    }

    override fun setVenueOverviewScreenFirstRun(value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit();
        editor.putBoolean(PREF_KEY_IS_VENUE_OVERVIEW_SCREEN_FIRST_RUN, value);
        editor.commit()
//        editor.apply()
    }

    override fun getVenueOverviewScreenFirstRun(): Boolean {
        return sharedPreferences.getBoolean(PREF_KEY_IS_VENUE_OVERVIEW_SCREEN_FIRST_RUN, true);
    }

}
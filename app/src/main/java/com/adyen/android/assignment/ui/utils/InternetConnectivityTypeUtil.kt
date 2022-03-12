package com.adyen.android.assignment.ui.utils

import android.app.Application
import android.content.Context
import android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Saturday 12th March, 2022.
 */


object InternetConnectivityTypeUtil {

    const val TYPE_NOT_CONNECTED: Int = 0
    const val TYPE_WIFI: Int = 1
    const val TYPE_MOBILE: Int = 2
    const val TYPE_ETHERNET: Int = 3

    /**
     * For a complete list of broadcast actions, see the broadcast_actions.txt file in the Android
     * SDK. Each broadcast action has a constant field associated with it. For example, the value of
     * the constant [android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED] is
     * "android.intent.action.AIRPLANE_MODE". Documentation for each broadcast action is available
     * in its associated constant field.
     * Here's how to find the broadcast_actions.txt file in the Android SDK:
     * On Windows, C:/Users/<username>/AppData/Local/Android/Sdk/platforms/<android-sdk-version>/data/broadcast_actions.txt
     * E.g.: C:/Users/username/AppData/Local/Android/Sdk/platforms/android-30/data/broadcast_actions.txt
     */
    const val ACTION_CONNECTIVITY_CHANGE: String = "android.net.conn.CONNECTIVITY_CHANGE"
    const val ACTION_WIFI_STATE_CHANGED: String = "android.net.wifi.WIFI_STATE_CHANGED"

    fun getConnectivityType(context: Context): Int {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return TYPE_NOT_CONNECTED
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return TYPE_NOT_CONNECTED

            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> TYPE_WIFI
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> TYPE_MOBILE
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> TYPE_ETHERNET
                else -> TYPE_NOT_CONNECTED
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> TYPE_WIFI
                    ConnectivityManager.TYPE_MOBILE -> TYPE_MOBILE
                    ConnectivityManager.TYPE_ETHERNET -> TYPE_ETHERNET
                    else -> TYPE_NOT_CONNECTED
                }
            }
        }

        return TYPE_NOT_CONNECTED
    }

}
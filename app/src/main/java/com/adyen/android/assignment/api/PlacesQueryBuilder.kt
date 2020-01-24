package com.adyen.android.assignment.api

import com.adyen.android.assignment.BuildConfig
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

abstract class PlacesQueryBuilder {
    private val baseQueryParams by lazy {
        mapOf(
            "client_id" to BuildConfig.CLIENT_ID,
            "client_secret" to BuildConfig.CLIENT_SECRET
        )
    }

    fun build(): Map<String, String> {
        val queryParams = HashMap(baseQueryParams)
        queryParams["v"] = dateFormat.format(Date())
        putQueryParams(queryParams)
        return queryParams
    }

    abstract fun putQueryParams(queryParams: MutableMap<String, String>)

    companion object {
        private val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.ROOT)
    }
}

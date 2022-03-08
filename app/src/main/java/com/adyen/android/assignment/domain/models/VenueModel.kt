package com.adyen.android.assignment.domain.models

import com.adyen.android.assignment.data.datasources.local.frameworks.room.entities.VenueEntity
import com.adyen.android.assignment.data.datasources.remote.frameworks.retrofit.models.venue.VenueDataTransferObject
import com.adyen.android.assignment.domain.models.mappers.VenueModelMapper
import com.adyen.android.assignment.ui.uimodels.VenueUiModel
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_0
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_1
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_2
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_3
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_4
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_5
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_6
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VENUE_EXTRAS_KEY_DISTANCE
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VENUE_EXTRAS_KEY_LATITUDE
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VENUE_EXTRAS_KEY_LONGITUDE


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


data class VenueModel(
    val name: String,
    val category: String,
    val iconPrefix: String,
    val iconSuffix: String,
    val distance: Int,
    val latitude: Double,
    val longitude: Double
) {

    companion object {

        const val VAR_ARG_SIZE = 7

        const val VENUE_EXTRAS_MAP_SIZE_UI_MODEL = 3

        fun newInstance(dataTransferObjectVenue: VenueDataTransferObject): VenueModel {
            val name: String = dataTransferObjectVenue.name
            val category: String = dataTransferObjectVenue.categories.first().name
            val iconPrefix: String = dataTransferObjectVenue.categories.first().icon.prefix
            val iconSuffix: String = dataTransferObjectVenue.categories.first().icon.suffix
            val distance: Int = dataTransferObjectVenue.distance
            val latitude: Double = dataTransferObjectVenue.geocode.main.latitude
            val longitude: Double = dataTransferObjectVenue.geocode.main.longitude

            return VenueModel(
                name = name,
                category = category,
                iconPrefix = iconPrefix,
                iconSuffix = iconSuffix,
                distance = distance,
                latitude = latitude,
                longitude = longitude
            )
        }

        fun newInstance(entityVenue: VenueEntity): VenueModel {
            val name: String = entityVenue.name
            val category: String = entityVenue.category
            val iconPrefix: String = entityVenue.iconPrefix
            val iconSuffix: String = entityVenue.iconSuffix
            val distance: Int = entityVenue.distance
            val latitude: Double = entityVenue.latitude
            val longitude: Double = entityVenue.longitude

            return VenueModel(
                name = name,
                category = category,
                iconPrefix = iconPrefix,
                iconSuffix = iconSuffix,
                distance = distance,
                latitude = latitude,
                longitude = longitude
            )
        }

        fun newInstance(uiModelVenue: VenueUiModel, venueExtras: Map<String, Any>): VenueModel {
            val verifiedVenueExtras: Map<String, Any> = VenueModelMapper.verifyUiModelVenueExtras(venueExtras)

            val distance: Int = verifiedVenueExtras.get(VENUE_EXTRAS_KEY_DISTANCE) as Int
            val latitude: Double = verifiedVenueExtras.get(VENUE_EXTRAS_KEY_LATITUDE) as Double
            val longitude: Double = verifiedVenueExtras.get(VENUE_EXTRAS_KEY_LONGITUDE) as Double
            val name: String = uiModelVenue.name
            val category: String = uiModelVenue.category
            val iconPrefix: String = uiModelVenue.iconPrefix
            val iconSuffix: String = uiModelVenue.iconSuffix

            return VenueModel(
                name = name,
                category = category,
                iconPrefix = iconPrefix,
                iconSuffix = iconSuffix,
                distance = distance,
                latitude = latitude,
                longitude = longitude
            )
        }

        fun newInstance(vararg venueSampleData: Any): VenueModel {
            val verifiedVenueSampleData: Array<out Any> = VenueModelMapper.verifyVenueSampleData(venueSampleData)

            val venueSampleDataName = verifiedVenueSampleData[VAR_ARG_INDEX_0] as String
            val venueSampleDataCategory = verifiedVenueSampleData[VAR_ARG_INDEX_1] as String
            val venueSampleDataIconPrefix = verifiedVenueSampleData[VAR_ARG_INDEX_2] as String
            val venueSampleDataIconSuffix = verifiedVenueSampleData[VAR_ARG_INDEX_3] as String
            val venueSampleDataDistance = verifiedVenueSampleData[VAR_ARG_INDEX_4] as Int
            val venueSampleDataLatitude = verifiedVenueSampleData[VAR_ARG_INDEX_5] as Double
            val venueSampleDataLongitude = verifiedVenueSampleData[VAR_ARG_INDEX_6] as Double

            return VenueModel(
                name = venueSampleDataName,
                category = venueSampleDataCategory,
                iconPrefix = venueSampleDataIconPrefix,
                iconSuffix = venueSampleDataIconSuffix,
                distance = venueSampleDataDistance,
                latitude = venueSampleDataLatitude,
                longitude = venueSampleDataLongitude
            )
        }

    }

}

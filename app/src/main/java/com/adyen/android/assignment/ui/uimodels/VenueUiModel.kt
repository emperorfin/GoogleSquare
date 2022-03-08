package com.adyen.android.assignment.ui.uimodels

import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.ui.uimodels.mappers.VenueUiModelMapper
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


data class VenueUiModel(
    val name: String,
    val category: String,
    val iconPrefix: String,
    val iconSuffix: String,
    val distance: String,
    val latitude: String,
    val longitude: String
) {

    companion object {

        const val VAR_ARG_SIZE = 7

        const val VENUE_EXTRAS_MAP_SIZE_DOMAIN_MODEL = 3

        fun newInstance(domainModelVenue: VenueModel, venueExtras: Map<String, Any>): VenueUiModel {
            val verifiedVenueExtras: Map<String, Any> = VenueUiModelMapper.verifyDomainModelVenueExtras(venueExtras)

            val distance: String = verifiedVenueExtras.get(VENUE_EXTRAS_KEY_DISTANCE) as String
            val latitude: String = verifiedVenueExtras.get(VENUE_EXTRAS_KEY_LATITUDE) as String
            val longitude: String = verifiedVenueExtras.get(VENUE_EXTRAS_KEY_LONGITUDE) as String
            val name: String = domainModelVenue.name
            val category: String = domainModelVenue.category
            val iconPrefix: String = domainModelVenue.iconPrefix
            val iconSuffix: String = domainModelVenue.iconSuffix

            return VenueUiModel(
                name = name,
                category = category,
                iconPrefix = iconPrefix,
                iconSuffix = iconSuffix,
                distance = distance,
                latitude = latitude,
                longitude = longitude
            )
        }

        fun newInstance(vararg venueSampleData: Any): VenueUiModel {
            val verifiedVenueSampleData: Array<out Any> = VenueUiModelMapper.verifyVenueSampleData(venueSampleData)

            val venueSampleDataName = verifiedVenueSampleData[VAR_ARG_INDEX_0] as String
            val venueSampleDataCategory = verifiedVenueSampleData[VAR_ARG_INDEX_1] as String
            val venueSampleDataIconPrefix = verifiedVenueSampleData[VAR_ARG_INDEX_2] as String
            val venueSampleDataIconSuffix = verifiedVenueSampleData[VAR_ARG_INDEX_3] as String
            val venueSampleDataDistance = verifiedVenueSampleData[VAR_ARG_INDEX_4] as String
            val venueSampleDataLatitude = verifiedVenueSampleData[VAR_ARG_INDEX_5] as String
            val venueSampleDataLongitude = verifiedVenueSampleData[VAR_ARG_INDEX_6] as String

            return VenueUiModel(
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

    val iconUrl: String = iconPrefix + iconSuffix

}

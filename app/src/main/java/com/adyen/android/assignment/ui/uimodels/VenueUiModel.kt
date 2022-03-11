package com.adyen.android.assignment.ui.uimodels

import com.adyen.android.assignment.R
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
import com.adyen.android.assignment.domain.models.VenueModel.Companion.NO_CATEGORY_PREFIX
import com.adyen.android.assignment.domain.models.VenueModel.Companion.NO_CATEGORY_SUFFIX


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

        const val ICON_PIXEL_32 = 32
        const val ICON_PIXEL_44 = 44
        const val ICON_PIXEL_64 = 64
        const val ICON_PIXEL_88 = 88
        const val ICON_PIXEL_120 = 120

        const val ICON_BACKGROUND = "bg_"

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

    /**
     * Category Icons ( https://developer.foursquare.com/reference/places-photos-guide#category-icons )
     * Icons for categories may be returned in the categories response field and are structured in a
     * similar prefix + size + suffix format. All icons are square images.
     * Sample Category JSON:
     * {
     *   "id":10001
     *   "name":"Amusement Park"
     *   "icon":{
     *       "prefix":"https://ss3.4sqi.net/img/categories_v2/arts_entertainment/themepark_"
     *       "suffix":".png"
     *   }
     * }
     * To obtain an icon, the only acceptable size values are one of: 32, 44, 64, 88, or 120 (pixels).
     * For example, the Amusement Park icon url is: https://ss3.4sqi.net/img/categories_v2/arts_entertainment/themepark_120.png
     * Additionally, a background can be added to any icon by adding bg_ to the URL
     * (i.e. https://ss3.4sqi.net/img/categories_v2/arts_entertainment/themepark_bg_120.png
     */
    val iconUrl: String = iconPrefix + ICON_BACKGROUND + ICON_PIXEL_120 + iconSuffix

}

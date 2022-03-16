package com.adyen.android.assignment.data.datasources.local.frameworks.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.adyen.android.assignment.data.datasources.local.frameworks.room.entities.mappers.VenueEntityMapper
import com.adyen.android.assignment.domain.models.VenueModel
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_0
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_1
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_2
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_3
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_4
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_5
import com.adyen.android.assignment.domain.constants.VenueExtrasKeysConstant.VAR_ARG_INDEX_6


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


@Entity(
    tableName = VenueEntity.TABLE_NAME,
    primaryKeys = [VenueEntity.COLUMN_INFO_LATITUDE, VenueEntity.COLUMN_INFO_LONGITUDE]
)
data class VenueEntity(
    @ColumnInfo(name = COLUMN_INFO_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_INFO_CATEGORY)
    val category: String,
    @ColumnInfo(name = COLUMN_INFO_ICON_PREFIX)
    val iconPrefix: String,
    @ColumnInfo(name = COLUMN_INFO_ICON_SUFFIX)
    val iconSuffix: String,
    @ColumnInfo(name = COLUMN_INFO_DISTANCE)
    val distance: Int,
    @ColumnInfo(name = COLUMN_INFO_LATITUDE)
    val latitude: Double,
    @ColumnInfo(name = COLUMN_INFO_LONGITUDE)
    val longitude: Double
) {

    companion object {

        const val TABLE_NAME = "table_venues"

        const val COLUMN_INFO_NAME = "name"
        const val COLUMN_INFO_CATEGORY = "category"
        const val COLUMN_INFO_ICON_PREFIX = "icon_prefix"
        const val COLUMN_INFO_ICON_SUFFIX = "icon_suffix"
        const val COLUMN_INFO_DISTANCE = "distance"
        const val COLUMN_INFO_LATITUDE = "latitude"
        const val COLUMN_INFO_LONGITUDE = "longitude"

        const val VAR_ARG_SIZE = 7

        fun newInstance(domainModelVenue: VenueModel): VenueEntity {
            val name: String = domainModelVenue.name
            val category: String = domainModelVenue.category
            val iconPrefix: String = domainModelVenue.iconPrefix
            val iconSuffix: String = domainModelVenue.iconSuffix
            val distance: Int = domainModelVenue.distance
            val latitude: Double = domainModelVenue.latitude
            val longitude: Double = domainModelVenue.longitude

            return VenueEntity(
                name = name,
                category = category,
                iconPrefix = iconPrefix,
                iconSuffix = iconSuffix,
                distance = distance,
                latitude = latitude,
                longitude = longitude
            )
        }

        fun newInstance(vararg venueSampleData: Any): VenueEntity {
            val verifiedVenueSampleData: Array<out Any> =
                VenueEntityMapper.verifyVenueSampleData(venueSampleData)

            val venueSampleDataName = verifiedVenueSampleData[VAR_ARG_INDEX_0] as String
            val venueSampleDataCategory = verifiedVenueSampleData[VAR_ARG_INDEX_1] as String
            val venueSampleDataIconPrefix = verifiedVenueSampleData[VAR_ARG_INDEX_2] as String
            val venueSampleDataIconSuffix = verifiedVenueSampleData[VAR_ARG_INDEX_3] as String
            val venueSampleDataDistance = verifiedVenueSampleData[VAR_ARG_INDEX_4] as Int
            val venueSampleDataLatitude = verifiedVenueSampleData[VAR_ARG_INDEX_5] as Double
            val venueSampleDataLongitude = verifiedVenueSampleData[VAR_ARG_INDEX_6] as Double

            return VenueEntity(
                name = venueSampleDataName,
                category = venueSampleDataCategory,
                iconPrefix = venueSampleDataIconPrefix,
                iconSuffix = venueSampleDataIconSuffix,
                distance = venueSampleDataDistance,
                latitude = venueSampleDataLatitude,
                longitude = venueSampleDataLongitude
            )
        }

        fun newInstance(
            name: String,
            category: String,
            iconPrefix: String,
            iconSuffix: String,
            distance: Int,
            latitude: Double,
            longitude: Double
        ): VenueEntity {

            return VenueEntity(
                name = name,
                category = category,
                iconPrefix = iconPrefix,
                iconSuffix = iconSuffix,
                distance = distance,
                latitude = latitude,
                longitude = longitude
            )
        }
    }

}

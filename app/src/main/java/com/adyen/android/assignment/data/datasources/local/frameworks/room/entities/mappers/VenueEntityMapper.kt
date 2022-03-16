package com.adyen.android.assignment.data.datasources.local.frameworks.room.entities.mappers

import com.adyen.android.assignment.data.datasources.local.frameworks.room.entities.VenueEntity
import com.adyen.android.assignment.data.datasources.local.frameworks.room.entities.VenueEntity.Companion.VAR_ARG_SIZE
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


class VenueEntityMapper {

    companion object {

        fun verifyVenueSampleData(vararg venueSampleData: Any): Array<out Any> {
            if (venueSampleData.size < VAR_ARG_SIZE)
                throw IllegalArgumentException(
                    "$venueSampleData size must not be less than $VAR_ARG_SIZE"
                )

            if (venueSampleData[VAR_ARG_INDEX_0] !is String)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_0 in $venueSampleData must be of type String."
                )

            if (venueSampleData[VAR_ARG_INDEX_1] !is String)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_1 in $venueSampleData must be of type String."
                )

            if (venueSampleData[VAR_ARG_INDEX_2] !is String)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_2 in $venueSampleData must be of type String."
                )

            if (venueSampleData[VAR_ARG_INDEX_3] !is String)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_3 in $venueSampleData must be of type String."
                )

            if (venueSampleData[VAR_ARG_INDEX_4] !is Int)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_4 in $venueSampleData must be of type Int."
                )

            if (venueSampleData[VAR_ARG_INDEX_5] !is Double)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_5 in $venueSampleData must be of type Double."
                )

            if (venueSampleData[VAR_ARG_INDEX_6] !is Double)
                throw IllegalArgumentException(
                    "Element at index $VAR_ARG_INDEX_6 in $venueSampleData must be of type Double."
                )

            return venueSampleData
        }

    }

    fun transform(domainModelVenue: VenueModel): VenueEntity =
        VenueEntity.newInstance(domainModelVenue)
}
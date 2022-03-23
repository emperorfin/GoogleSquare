package emperorfin.android.googlesquare.data.utils

import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entities.VenueEntity

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_4
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_5
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_6
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_7
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_8
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_9
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.NAME_10

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_4
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_5
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_6
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_7
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_8
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_9
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.CATEGORY_10

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_4
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_5
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_6
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_7
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_8
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_9
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_PREFIX_10

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_4
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_5
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_6
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_7
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_8
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_9
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.ICON_SUFFIX_10

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_4
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_5
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_6
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_7
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_8
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_9
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.DISTANCE_10

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_4
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_5
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_6
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_7
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_8
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_9
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LATITUDE_10

import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_1
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_2
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_3
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_4
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_5
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_6
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_7
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_8
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_9
import emperorfin.android.googlesquare.data.constants.VenuesSampleDataConstants.LONGITUDE_10


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


object VenuesDataGeneratorUtil {

    fun getVenueEntityArrayListSampleData(): ArrayList<VenueEntity> {
        val venues: ArrayList<VenueEntity> = ArrayList()

        var venue = VenueEntity.newInstance(
            NAME_1,
            CATEGORY_1,
            ICON_PREFIX_1,
            ICON_SUFFIX_1,
            DISTANCE_1,
            LATITUDE_1,
            LONGITUDE_1
        )

        venues.add(venue)

        venue = VenueEntity.newInstance(
            NAME_2,
            CATEGORY_2,
            ICON_PREFIX_2,
            ICON_SUFFIX_2,
            DISTANCE_2,
            LATITUDE_2,
            LONGITUDE_2
        )

        venues.add(venue)

        venue = VenueEntity.newInstance(
            NAME_3,
            CATEGORY_3,
            ICON_PREFIX_3,
            ICON_SUFFIX_3,
            DISTANCE_3,
            LATITUDE_3,
            LONGITUDE_3
        )

        venues.add(venue)

        venue = VenueEntity.newInstance(
            NAME_4,
            CATEGORY_4,
            ICON_PREFIX_4,
            ICON_SUFFIX_4,
            DISTANCE_4,
            LATITUDE_4,
            LONGITUDE_4
        )

        venues.add(venue)

        venue = VenueEntity.newInstance(
            NAME_5,
            CATEGORY_5,
            ICON_PREFIX_5,
            ICON_SUFFIX_5,
            DISTANCE_5,
            LATITUDE_5,
            LONGITUDE_5
        )

        venues.add(venue)

        venue = VenueEntity.newInstance(
            NAME_6,
            CATEGORY_6,
            ICON_PREFIX_6,
            ICON_SUFFIX_6,
            DISTANCE_6,
            LATITUDE_6,
            LONGITUDE_6
        )

        venues.add(venue)

        venue = VenueEntity.newInstance(
            NAME_7,
            CATEGORY_7,
            ICON_PREFIX_7,
            ICON_SUFFIX_7,
            DISTANCE_7,
            LATITUDE_7,
            LONGITUDE_7
        )

        venues.add(venue)

        venue = VenueEntity.newInstance(
            NAME_8,
            CATEGORY_8,
            ICON_PREFIX_8,
            ICON_SUFFIX_8,
            DISTANCE_8,
            LATITUDE_8,
            LONGITUDE_8
        )

        venues.add(venue)

        venue = VenueEntity.newInstance(
            NAME_9,
            CATEGORY_9,
            ICON_PREFIX_9,
            ICON_SUFFIX_9,
            DISTANCE_9,
            LATITUDE_9,
            LONGITUDE_9
        )

        venues.add(venue)

        venue = VenueEntity.newInstance(
            NAME_10,
            CATEGORY_10,
            ICON_PREFIX_10,
            ICON_SUFFIX_10,
            DISTANCE_10,
            LATITUDE_10,
            LONGITUDE_10
        )

        venues.add(venue)

        return venues
    }

}
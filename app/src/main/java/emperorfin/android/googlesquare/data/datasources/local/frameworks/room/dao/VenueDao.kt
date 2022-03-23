package emperorfin.android.googlesquare.data.datasources.local.frameworks.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entities.VenueEntity
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entities.VenueEntity.Companion.TABLE_NAME
import emperorfin.android.googlesquare.data.datasources.local.frameworks.room.entities.VenueEntity.Companion.COLUMN_INFO_NAME


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


@Dao
interface VenueDao {

    @Query("SELECT COUNT(*) FROM $TABLE_NAME")
    suspend fun countVenues(): Int

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_INFO_NAME ASC")
    suspend fun getVenues(): List<VenueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVenue(venue: VenueEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVenues(venues: List<VenueEntity>): List<Long>

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteVenues(): Int
}
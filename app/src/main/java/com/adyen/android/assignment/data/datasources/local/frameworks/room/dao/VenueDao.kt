package com.adyen.android.assignment.data.datasources.local.frameworks.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.adyen.android.assignment.data.datasources.local.frameworks.room.entities.VenueEntity


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


@Dao
interface VenueDao {

    @Query("SELECT COUNT(*) FROM ${VenueEntity.TABLE_NAME}")
    suspend fun countVenues(): Int

    @Query("SELECT * FROM ${VenueEntity.TABLE_NAME} ORDER BY ${VenueEntity.COLUMN_INFO_NAME} ASC")
    suspend fun getVenues(): List<VenueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVenue(venue: VenueEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVenues(venues: List<VenueEntity>): List<Long>

    @Query("DELETE FROM ${VenueEntity.TABLE_NAME}")
    suspend fun deleteVenues(): Int
}
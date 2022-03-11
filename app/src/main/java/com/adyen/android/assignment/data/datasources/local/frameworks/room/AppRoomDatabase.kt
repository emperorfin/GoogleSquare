package com.adyen.android.assignment.data.datasources.local.frameworks.room

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.adyen.android.assignment.data.datasources.local.frameworks.room.dao.VenueDao
import com.adyen.android.assignment.data.datasources.local.frameworks.room.entities.VenueEntity
import com.adyen.android.assignment.data.utils.VenuesDataGeneratorUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


@Database(entities = [VenueEntity::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract val mVenueDao: VenueDao

    companion object {

        private const val DATABASE_NAME = "database_app"

        private var isDatabaseAlreadyPopulated: Boolean = false

        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        private val TAG: String = AppRoomDatabase::class.java.simpleName

        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase{

            synchronized(this){
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppRoomDatabase::class.java,
                        DATABASE_NAME
                    )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            coroutineScope.launch {
                                // This is now commented out since real venue data are being cached
                                // to the database.
                                //populateInitialVenuesSampleDataUsingSqliteDatabaseWithCoroutineThread(db, VenuesDataGeneratorUtil.getVenueEntityArrayListSampleData())
                            }
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                        }

                        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                            super.onDestructiveMigration(db)
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()

                    INSTANCE = instance
                }

                return instance
            }

        }

        private suspend fun populateInitialVenuesSampleDataUsingSqliteDatabaseWithCoroutineThread(db: SupportSQLiteDatabase, venues: List<VenueEntity>) {
            // Unused at the moment.
            if (isDatabaseAlreadyPopulated)
                return

            db.beginTransaction()

            try {
                val initialVenueValues: ContentValues = ContentValues()
                for (venue in venues){
                    initialVenueValues.put(VenueEntity.COLUMN_INFO_NAME, "${venue.name} (room sample data)")
                    initialVenueValues.put(VenueEntity.COLUMN_INFO_CATEGORY, "${venue.category}")
                    initialVenueValues.put(VenueEntity.COLUMN_INFO_ICON_PREFIX, "${venue.iconPrefix}")
                    initialVenueValues.put(VenueEntity.COLUMN_INFO_ICON_SUFFIX, "${venue.iconSuffix}")
                    initialVenueValues.put(VenueEntity.COLUMN_INFO_DISTANCE, "${venue.distance}")
                    initialVenueValues.put(VenueEntity.COLUMN_INFO_LATITUDE, "${venue.latitude}")
                    initialVenueValues.put(VenueEntity.COLUMN_INFO_LONGITUDE, "${venue.longitude}")

                    db.insert(VenueEntity.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE, initialVenueValues)
                }

                db.setTransactionSuccessful()
            }finally {
                db.endTransaction()
            }
        }

    }

}
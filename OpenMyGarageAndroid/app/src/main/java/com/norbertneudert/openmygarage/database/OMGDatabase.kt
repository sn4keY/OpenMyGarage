package com.norbertneudert.openmygarage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [EntryLog::class, StoredPlate::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class OMGDatabase : RoomDatabase() {
    abstract val entryLog : EntryLogDao
    abstract val storedPlate : StoredPlateDao

    companion object {
        @Volatile
        private var INSTANCE: OMGDatabase? = null

        fun getInstance(context: Context) : OMGDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OMGDatabase::class.java,
                        "omg_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
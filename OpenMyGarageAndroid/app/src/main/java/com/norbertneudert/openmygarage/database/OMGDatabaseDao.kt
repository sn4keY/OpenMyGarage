package com.norbertneudert.openmygarage.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface OMGDatabaseDao {
    @Query("SELECT * FROM entry_log_table")
    fun getEntryLog(): LiveData<List<EntryLog>>

    @Query("SELECT * FROM stored_plates_table")
    fun getStoredPlates() : LiveData<List<StoredPlates>>

    @Query("DELETE FROM entry_log_table")
    fun clearEntryLog()

    @Query("DELETE FROM stored_plates_table")
    fun clearStoredPlates()
}
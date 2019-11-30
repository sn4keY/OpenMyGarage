package com.norbertneudert.openmygarage.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EntryLogDao {
    @Query("SELECT * FROM entry_log_table ORDER BY date DESC")
    fun getEntryLog(): LiveData<List<EntryLog>>

    @Query("DELETE FROM entry_log_table")
    fun clearEntryLog()

    @Insert
    fun insert(entryLog: EntryLog)
}
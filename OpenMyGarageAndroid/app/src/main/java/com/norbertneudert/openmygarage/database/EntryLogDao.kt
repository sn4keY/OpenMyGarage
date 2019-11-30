package com.norbertneudert.openmygarage.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.security.KeyStore
import java.util.*

@Dao
interface EntryLogDao {
    @Query("SELECT * FROM entry_log_table ORDER BY date DESC")
    fun getEntryLog(): LiveData<List<EntryLog>>

    @Query("DELETE FROM entry_log_table")
    fun clearEntryLog()

    @Insert
    fun insert(entryLog: EntryLog)

    @Query("SELECT * FROM entry_log_table WHERE :dateFrom < date < :dateUntil ")
    fun getEntryLogDate(dateFrom: Date, dateUntil: Date): LiveData<List<EntryLog>>
}
package com.norbertneudert.openmygarage.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StoredPlatesDao {
    @Query("SELECT * FROM stored_plates_table")
    fun getStoredPlates() : LiveData<List<StoredPlates>>

    @Query("DELETE FROM stored_plates_table")
    fun clearStoredPlates()

    @Insert
    fun insert(storedPlates: StoredPlates)
}
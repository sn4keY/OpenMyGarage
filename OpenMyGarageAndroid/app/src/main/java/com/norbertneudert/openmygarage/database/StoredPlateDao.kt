package com.norbertneudert.openmygarage.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StoredPlateDao {
    @Query("SELECT * FROM stored_plate_table")
    fun getStoredPlates(): LiveData<List<StoredPlate>>

    @Query("SELECT * FROM stored_plate_table WHERE plateId = :key")
    fun get(key: Long): StoredPlate?

    @Query("DELETE FROM stored_plate_table")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(storedPlate: StoredPlate)

    @Update
    fun update(storedPlate: StoredPlate)

    @Delete
    fun remove(storedPlate: StoredPlate)
}
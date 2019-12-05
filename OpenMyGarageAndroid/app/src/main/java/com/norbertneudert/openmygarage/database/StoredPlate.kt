package com.norbertneudert.openmygarage.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stored_plate_table")
data class StoredPlate (
    @PrimaryKey(autoGenerate = true)
    var plateId: Long = 0L,

    @ColumnInfo(name = "plate")
    var plate: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "outcome")
    var outcome: Outcome = Outcome.NOTIFY
)
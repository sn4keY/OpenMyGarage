package com.norbertneudert.openmygarage.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stored_plates_table")
data class StoredPlates(
    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0L,

    @ColumnInfo(name = "plate")
    val Plate: String = "",

    @ColumnInfo(name = "name")
    val Name: String = "",

    @ColumnInfo(name = "outcome")
    val Outcome: GateAction = GateAction.NOTIFY
)
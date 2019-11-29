package com.norbertneudert.openmygarage.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "entry_log_table")
data class EntryLog (
    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0L,

    @ColumnInfo(name = "plate")
    val Plate: String = "",

    @ColumnInfo(name = "date")
    val Time: Date = Calendar.getInstance().time,

    @ColumnInfo(name = "outcome")
    val Outcome: GateAction = GateAction.NOTIFY
)
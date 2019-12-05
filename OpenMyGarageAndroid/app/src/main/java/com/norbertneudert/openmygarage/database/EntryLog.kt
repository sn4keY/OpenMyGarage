package com.norbertneudert.openmygarage.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "entry_log_table")
data class EntryLog (
    @PrimaryKey(autoGenerate = true)
    var logId: Long = 0L,

    @ColumnInfo(name = "entry_time")
    val entryTime: Date = Calendar.getInstance().time,

    @ColumnInfo(name = "plate")
    var plate: String = "",

    @ColumnInfo(name = "outcome")
    var outcome: Outcome = Outcome.NOTIFY
)
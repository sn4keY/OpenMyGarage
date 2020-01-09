package com.norbertneudert.openmygarage.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "entry_log_table")
data class EntryLog (
    @PrimaryKey(autoGenerate = true)
    var logId: Long = 0L,

    @ColumnInfo(name = "entry_time")
    @Json(name = "time")
    val entryTime: Long = Long.MIN_VALUE,

    @ColumnInfo(name = "plate")
    @Json(name = "plate")
    var plate: String = "",

    @ColumnInfo(name = "outcome")
    @Json(name = "outcome")
    var outcome: Int = 1
)
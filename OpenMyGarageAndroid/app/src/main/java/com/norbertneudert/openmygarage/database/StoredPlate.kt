package com.norbertneudert.openmygarage.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "stored_plate_table")
data class StoredPlate (
    @PrimaryKey(autoGenerate = true)
    var plateId: Long = 0L,

    @Json(name = "plate")
    @ColumnInfo(name = "plate")
    var plate: String = "",

    @Json(name = "name")
    @ColumnInfo(name = "name")
    var name: String = "",

    @Json(name = "action")
    @ColumnInfo(name = "outcome")
    var outcome: Int = 1
)
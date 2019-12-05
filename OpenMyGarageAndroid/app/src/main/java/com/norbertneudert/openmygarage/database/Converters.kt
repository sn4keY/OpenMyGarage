package com.norbertneudert.openmygarage.database

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun intToOutcome(value: Int?): Outcome? {
        when(value) {
            1 -> return Outcome.OPEN
            2 -> return Outcome.NOTIFY
            3 -> return Outcome.REFUSE
            else -> return Outcome.NOTIFY
        }
    }

    @TypeConverter
    fun outcomeToInt(outcome: Outcome?): Int? {
        when(outcome) {
            Outcome.OPEN -> return 1
            Outcome.NOTIFY -> return 2
            Outcome.REFUSE -> return 3
            else -> return 2
        }
    }
}
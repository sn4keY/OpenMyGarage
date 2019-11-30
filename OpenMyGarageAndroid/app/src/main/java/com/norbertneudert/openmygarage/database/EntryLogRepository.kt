package com.norbertneudert.openmygarage.database

import androidx.lifecycle.LiveData
import java.util.*

class EntryLogRepository (val entryLogDao: EntryLogDao) {

    fun getEntryLogs(): LiveData<List<EntryLog>> {
        return entryLogDao.getEntryLog()
    }

    fun refreshDb() {
        entryLogDao.clearEntryLog()
        // TODO: call api
        // TODO: insert into db
    }

    fun searchEntryLogs(dateFrom: Date, dateUntil: Date): LiveData<List<EntryLog>> {
        return entryLogDao.getEntryLogDate(dateFrom, dateUntil)
    }
}
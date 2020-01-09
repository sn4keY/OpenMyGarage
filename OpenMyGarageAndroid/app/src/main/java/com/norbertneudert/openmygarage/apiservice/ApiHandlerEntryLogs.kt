package com.norbertneudert.openmygarage.apiservice

import android.util.Log
import com.norbertneudert.openmygarage.database.EntryLog
import com.norbertneudert.openmygarage.database.EntryLogDao
import kotlinx.coroutines.*

class ApiHandlerEntryLogs private constructor(private val entryLogsDB: EntryLogDao) {
    private var handlerJob = Job()
    private val coroutineScope = CoroutineScope(handlerJob + Dispatchers.Main)

    init {
        onClear()
        refreshDatabase()
    }

    companion object {
        @Volatile
        private var INSTANCE: ApiHandlerEntryLogs? = null

        fun getInstance(entryLogsDB: EntryLogDao) : ApiHandlerEntryLogs {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = ApiHandlerEntryLogs(entryLogsDB)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    fun refreshDatabase() : Boolean {
        coroutineScope.launch {
            val getEntryLogsDeferred = OMGApi.retrofitService.getEntryLogs()
            try {
                val listResult = getEntryLogsDeferred.await()
                Log.i("ApiHandler", "refreshDatabase: listResult count: " + listResult.count())
                populateEntryLogs(listResult)
            } catch (e: Exception) {
                Log.i("ApiHandler", "Refresh entryLogs failed: " + e.message)
            }
        }
        return false
    }

    private suspend fun populateEntryLogs(entryLogs: List<EntryLog>) {
        if (isLogsUpdated(entryLogs)) {
            val logList = getEntryLogs()
            Log.i("ApiHandler", "populateEntryLogs: entryLogs in DB: " + logList?.count())
            var index = if (logList?.count() != 0) { entryLogs.count() - (entryLogs.count() - logList!!.count())} else { 0 }
            Log.i("ApiHandler", "populateEntryLogs: index: $index")
            while (index < entryLogs.count()){
                withContext(Dispatchers.IO) {
                    entryLogsDB.insert(entryLogs[index])
                }
                index++
            }
        }
    }

    private suspend fun getEntryLogs() : List<EntryLog>? {
        return withContext(Dispatchers.IO) {
            entryLogsDB.getEntryLogsList()
        }
    }

    private suspend fun isLogsUpdated(entryLogs: List<EntryLog>) : Boolean {
        val log = getLastEntryLog()
        Log.i("ApiHandler", "isLogsUpdated: lastLog: " + log.toString())
        if (log?.entryTime == entryLogs.last().entryTime) {
            Log.i("ApiHandler", "isLogsUpdated: false")
            return false
        }
        Log.i("ApiHandler", "isLogsUpdated: true")
        return true
    }

    private suspend fun getLastEntryLog() : EntryLog? {
        return withContext(Dispatchers.IO) {
            entryLogsDB.getLastEntryLog()
        }
    }

    fun onClear() {
        coroutineScope.launch {
            clearEntryLogs()
        }
    }

    private suspend fun clearEntryLogs() {
        withContext(Dispatchers.IO) {
            entryLogsDB.clear()
        }
    }
}
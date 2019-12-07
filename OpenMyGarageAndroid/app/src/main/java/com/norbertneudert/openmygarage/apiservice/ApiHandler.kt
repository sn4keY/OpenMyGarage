package com.norbertneudert.openmygarage.apiservice

import android.util.Log
import com.norbertneudert.openmygarage.database.EntryLog
import com.norbertneudert.openmygarage.database.EntryLogDao
import kotlinx.coroutines.*

class ApiHandler(private val entryLogsDB: EntryLogDao) {
    private var handlerJob = Job()
    private val coroutineScope = CoroutineScope(handlerJob + Dispatchers.Main)

    init {
        refreshDatabase()
    }

    fun refreshDatabase() {
        coroutineScope.launch {
            clearEntryLogs()
            var getEntryLogsDeferred = OMGApi.retrofitService.getEntryLogs()
            try {
                var listResult = getEntryLogsDeferred.await()
                populateEntryLogs(listResult)
                Log.i("ApiHandler", listResult[0].plate)

            } catch (e: Exception) {
                Log.i("ApiHandler", e.message)
            }
        }
    }

    private suspend fun populateEntryLogs(entryLogs: List<EntryLog>){
        for (log in entryLogs) {
            withContext(Dispatchers.IO) {
                entryLogsDB.insert(log)
            }
        }
    }

    private suspend fun clearEntryLogs(){
        withContext(Dispatchers.IO) {
            entryLogsDB.clear()
        }
    }
}
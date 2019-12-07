package com.norbertneudert.openmygarage.apiservice

import android.util.Log
import com.norbertneudert.openmygarage.database.StoredPlate
import com.norbertneudert.openmygarage.database.StoredPlateDao
import kotlinx.coroutines.*

class ApiHandlerStoredPlates(private val storedplatesDB: StoredPlateDao) {
    private var handlerJob = Job()
    private val coroutineScope = CoroutineScope(handlerJob + Dispatchers.Main)

    init {
        refreshDatabase()
    }

    private fun refreshDatabase() {
        coroutineScope.launch {
            clearStoredPlates()
            val getStoredPlatesDeferred = OMGApi.retrofitService.getStoredPlates()
            try {
                val listResult = getStoredPlatesDeferred.await()
                populateStoredPlates(listResult)
            } catch (e: Exception) {
                Log.i("ApiHandler", "Refresh storedPlates failed: " + e.message)
            }
        }
    }

    private suspend fun populateStoredPlates(storedPlates: List<StoredPlate>) {
        for(plate in storedPlates) {
            withContext(Dispatchers.IO) {
                storedplatesDB.insert(plate)
            }
        }
    }

    private suspend fun clearStoredPlates() {
        withContext(Dispatchers.IO) {
            storedplatesDB.clear()
        }
    }
}
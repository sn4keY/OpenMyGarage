package com.norbertneudert.openmygarage.apiservice

import android.util.Log
import com.norbertneudert.openmygarage.database.StoredPlate
import com.norbertneudert.openmygarage.database.StoredPlateDao
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiHandlerStoredPlates(private val storedplatesDB: StoredPlateDao) {
    private var handlerJob = Job()
    private val coroutineScope = CoroutineScope(handlerJob + Dispatchers.Main)

    init {
        refreshDatabase()
    }

    fun postStoredPlate(storedPlate: StoredPlate, plateBefore: String){
        OMGApi.retrofitService.postStoredPlate(plateBefore, storedPlate).enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("ApiHandlerStoredPlates", t.message)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.i("ApiHandlerStoredPlates", response.message())
            }
        })
        refreshDatabase()
    }

    fun deleteStoredPlate(plate: String){
        OMGApi.retrofitService.deleteStoredPlate(plate).enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("ApiHandlerStoredPlates", t.message)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.i("ApiHandlerStoredPlates", response.message())
            }
        })
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
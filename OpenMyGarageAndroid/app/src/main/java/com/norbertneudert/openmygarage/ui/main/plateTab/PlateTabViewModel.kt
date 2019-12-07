package com.norbertneudert.openmygarage.ui.main.plateTab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.norbertneudert.openmygarage.database.Outcome
import com.norbertneudert.openmygarage.database.StoredPlate
import com.norbertneudert.openmygarage.database.StoredPlateDao
import kotlinx.coroutines.*

class PlateTabViewModel(val database: StoredPlateDao, application: Application) : AndroidViewModel(application) {
    val plates = database.getStoredPlates()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onEdit(storedPlate: StoredPlate) {
        uiScope.launch {
            edit(storedPlate)
        }
    }

    private suspend fun edit(storedPlate: StoredPlate) {
        withContext(Dispatchers.IO) {
            database.insert(storedPlate)
        }
    }

    fun onDelete(storedPlate: StoredPlate) {
        uiScope.launch {
            delete(storedPlate)
        }
    }

    private suspend fun delete(storedPlate: StoredPlate) {
        withContext(Dispatchers.IO) {
            database.remove(storedPlate)
        }
    }

    fun onClear(){
        uiScope.launch {
            clear()
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    fun onPopulate() {
        uiScope.launch {
            populateEntryLogs()
        }
    }

    private suspend fun populateEntryLogs() {
        withContext(Dispatchers.IO) {
            database.insert(StoredPlate(name="Norbi", plate = "ABC-123", outcome = Outcome.OPEN))
            database.insert(StoredPlate(name = "Joci", plate = "XYZ-123", outcome = Outcome.NOTIFY))
            database.insert(StoredPlate(name = "Laci", plate = "UJA-462", outcome = Outcome.REFUSE))
            database.insert(StoredPlate(name = "Gabi", plate = "IKA-515"))
        }
    }
}

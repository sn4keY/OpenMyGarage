package com.norbertneudert.openmygarage.ui.main.plateTab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
}

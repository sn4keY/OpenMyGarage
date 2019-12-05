package com.norbertneudert.openmygarage.ui.main.logTab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.norbertneudert.openmygarage.database.EntryLog
import com.norbertneudert.openmygarage.database.EntryLogDao
import com.norbertneudert.openmygarage.database.Outcome
import kotlinx.coroutines.*

class LogTabViewModel(val database: EntryLogDao, application: Application) : AndroidViewModel(application) {
    val logs = database.getEntryLogs()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
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
            database.insert(EntryLog(plate = "ABC-123", outcome = Outcome.OPEN))
            database.insert(EntryLog(plate = "XYZ-123"))
            database.insert(EntryLog(plate = "UJA-462"))
            database.insert(EntryLog(plate = "PIS-823", outcome = Outcome.OPEN))
            database.insert(EntryLog(plate = "BSR-312"))
            database.insert(EntryLog(plate = "ZAK-012"))
            database.insert(EntryLog(plate = "MKA-721"))
            database.insert(EntryLog(plate = "LUC-666", outcome = Outcome.OPEN))
            database.insert(EntryLog(plate = "GOD-420"))
            database.insert(EntryLog(plate = "UAE-999"))
        }
    }
}

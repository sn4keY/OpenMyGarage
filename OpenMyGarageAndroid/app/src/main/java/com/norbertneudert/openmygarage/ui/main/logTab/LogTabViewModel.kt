package com.norbertneudert.openmygarage.ui.main.logTab

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.norbertneudert.openmygarage.database.EntryLogDao
import kotlinx.coroutines.*

class LogTabViewModel(val database: EntryLogDao, application: Application) : AndroidViewModel(application) {
    val logs = database.getEntryLogs()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

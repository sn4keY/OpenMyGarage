package com.norbertneudert.openmygarage.ui.main.mainTab

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.norbertneudert.openmygarage.database.EntryLogDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainTabViewModel(val database: EntryLogDao, application: Application) : AndroidViewModel(application) {
    val logs = database.getEntryLogsLimited()

    init {
        Log.i("MainTabViewModel", "initialized")
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

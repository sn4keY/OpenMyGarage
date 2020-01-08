package com.norbertneudert.openmygarage.ui.main.logTab

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.norbertneudert.openmygarage.database.EntryLogDao
import java.lang.IllegalArgumentException

class LogTabViewModelFactory (private val dataSource: EntryLogDao, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogTabViewModel::class.java)) {
            return LogTabViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
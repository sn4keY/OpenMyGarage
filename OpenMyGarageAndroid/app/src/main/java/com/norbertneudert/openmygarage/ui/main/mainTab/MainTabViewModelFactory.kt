package com.norbertneudert.openmygarage.ui.main.mainTab

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.norbertneudert.openmygarage.database.EntryLogDao

class MainTabViewModelFactory (private val dataSource: EntryLogDao, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainTabViewModel::class.java)) {
            return MainTabViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
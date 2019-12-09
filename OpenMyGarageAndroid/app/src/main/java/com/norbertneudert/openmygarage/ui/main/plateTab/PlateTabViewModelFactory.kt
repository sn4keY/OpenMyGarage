package com.norbertneudert.openmygarage.ui.main.plateTab

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.norbertneudert.openmygarage.database.StoredPlateDao
import java.lang.IllegalArgumentException

class PlateTabViewModelFactory(private val dataSource: StoredPlateDao, private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlateTabViewModel::class.java)) {
            return PlateTabViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.norbertneudert.openmygarage.ui.main.mainTab

import androidx.lifecycle.ViewModel
import com.norbertneudert.openmygarage.database.EntryLogRepository

class MainViewModel(val repository: EntryLogRepository) : ViewModel() {
    val text = "ASD"
}

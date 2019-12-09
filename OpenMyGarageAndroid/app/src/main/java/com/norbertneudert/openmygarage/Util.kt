package com.norbertneudert.openmygarage

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var plateTextView: TextView
    var timeTextView: TextView
    var outcomeTextView: TextView

    init {
        plateTextView = itemView.findViewById(R.id.plate_logs_tw)
        timeTextView = itemView.findViewById(R.id.time_logs_tw)
        outcomeTextView = itemView.findViewById(R.id.outcome_logs_tw)
    }
}

class PlatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var nameTextView: TextView
    var plateTextView: TextView
    var outcomeTextView: TextView
    var editButton: Button
    var deleteButton: Button

    init {
        nameTextView = itemView.findViewById(R.id.name_plates_tw) as TextView
        plateTextView = itemView.findViewById(R.id.plate_plates_tw) as TextView
        outcomeTextView = itemView.findViewById(R.id.outcome_plates_tw) as TextView
        editButton = itemView.findViewById(R.id.edit_button) as Button
        deleteButton = itemView.findViewById(R.id.delete_button) as Button
    }
}
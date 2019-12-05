package com.norbertneudert.openmygarage.ui.main.logTab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.norbertneudert.openmygarage.LogsViewHolder
import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.database.EntryLog
import java.text.SimpleDateFormat

class EntryLogAdapter : RecyclerView.Adapter<LogsViewHolder>() {
    var data = listOf<EntryLog>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val item = data[position]
        holder.plateTextView.text = item.plate
        val time = item.entryTime
        holder.timeTextView.text = SimpleDateFormat("yyyy.MM.dd HH:mm").format(time)
        holder.outcomeTextView.text = item.outcome.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.logs_item_view, parent, false)
        return LogsViewHolder(view)
    }
}
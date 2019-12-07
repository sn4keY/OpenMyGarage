package com.norbertneudert.openmygarage.ui.main.logTab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.norbertneudert.openmygarage.LogsViewHolder
import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.database.EntryLog
import java.text.SimpleDateFormat
import java.util.*

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
        val epochMicrotimeDiff = 621355968000000000L
        val time = Date((item.entryTime - epochMicrotimeDiff) / 10000)
        holder.timeTextView.text = SimpleDateFormat("yyyy.MM.dd HH:mm").format(time)
        holder.outcomeTextView.text = getOutcomeString(item.outcome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.logs_item_view, parent, false)
        return LogsViewHolder(view)
    }

    private fun getOutcomeString(value: Int): String{
        when(value){
            0 -> return "OPEN"
            2 -> return "REFUSE"
            else -> return "NOTIFY"
        }
    }
}
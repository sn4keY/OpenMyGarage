package com.norbertneudert.openmygarage.ui.main.logTab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norbertneudert.openmygarage.database.EntryLog
import com.norbertneudert.openmygarage.databinding.LogsItemViewBinding
import java.text.SimpleDateFormat
import java.util.*

class EntryLogAdapter : ListAdapter<EntryLog, EntryLogAdapter.ViewHolder>(LogDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: LogsItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EntryLog) {
            binding.plateLogsTw.text = item.plate
            val epochMicrotimeDiff = 621355968000000000L
            val time = Date((item.entryTime - epochMicrotimeDiff) / 10000)
            binding.timeLogsTw.text = SimpleDateFormat("yyyy.MM.dd HH:mm").format(time)
            binding.outcomeLogsTw.text = getOutcomeString(item.outcome)
        }
        private fun getOutcomeString(value: Int): String{
            when(value){
                0 -> return "OPEN"
                2 -> return "REFUSE"
                else -> return "NOTIFY"
            }
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LogsItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class LogDiffCallback : DiffUtil.ItemCallback<EntryLog>() {
    override fun areItemsTheSame(oldItem: EntryLog, newItem: EntryLog): Boolean {
        return oldItem.logId == newItem.logId
    }

    override fun areContentsTheSame(oldItem: EntryLog, newItem: EntryLog): Boolean {
        return oldItem == newItem
    }
}
package com.norbertneudert.openmygarage.ui.main.plateTab

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.norbertneudert.openmygarage.apiservice.ApiHandlerStoredPlates
import com.norbertneudert.openmygarage.database.StoredPlate
import com.norbertneudert.openmygarage.databinding.PlatesItemViewBinding

class PlateAdapter(val apiHandler: ApiHandlerStoredPlates,private val supportFragmentManager: FragmentManager,private val parent: Fragment) : androidx.recyclerview.widget.ListAdapter<StoredPlate, PlateAdapter.ViewHolder>(PlateDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, parent, supportFragmentManager, apiHandler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: PlatesItemViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StoredPlate, parent: Fragment, supportFragmentManager: FragmentManager, apiHandler: ApiHandlerStoredPlates) {
            binding.namePlatesTw.text = item.name
            binding.platePlatesTw.text = item.plate
            binding.outcomePlatesTw.text = getOutcomeString(item.outcome)
            binding.editButton.setOnClickListener {
                val editor = EditPlateFragment.newInstance(item)
                editor.setTargetFragment(parent, 300)
                editor.show(supportFragmentManager, "dialog")
            }
            binding.deleteButton.setOnClickListener {
                apiHandler.deleteStoredPlate(item.plate)
            }
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
                val binding = PlatesItemViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class PlateDiffCallback : DiffUtil.ItemCallback<StoredPlate>() {
    override fun areItemsTheSame(oldItem: StoredPlate, newItem: StoredPlate): Boolean {
        return oldItem.plateId == newItem.plateId
    }

    override fun areContentsTheSame(oldItem: StoredPlate, newItem: StoredPlate): Boolean {
        return oldItem == newItem
    }
}
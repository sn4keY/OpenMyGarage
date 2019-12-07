package com.norbertneudert.openmygarage.ui.main.plateTab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.database.Outcome
import com.norbertneudert.openmygarage.database.StoredPlate
import com.norbertneudert.openmygarage.databinding.EditPlateFragmentBinding

class EditPlateFragment(var storedPlate: StoredPlate) : DialogFragment() {
    companion object {
        fun newInstance(storedPlate: StoredPlate) = EditPlateFragment(storedPlate)
    }

    interface EditPlateDialogListener {
        fun onFinishedEditing(storedPlate: StoredPlate)
    }

    private lateinit var binding: EditPlateFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_plate_fragment, container, false)
        binding.editNameIt.setText(storedPlate.name)
        binding.editPlateIt.setText(storedPlate.plate)
        binding.editPlateOutcome.check(when(storedPlate.outcome) {
            Outcome.OPEN -> R.id.edit_plate_rb_open
            Outcome.REFUSE -> R.id.edit_plate_rb_refuse
            else -> R.id.edit_plate_rb_notify
        })

        binding.editDoneBtn.setOnClickListener {
            val listener = targetFragment as EditPlateDialogListener
            val name = binding.editNameIt.text.toString()
            val plate = binding.editPlateIt.text.toString()
            val outcome = when(binding.editPlateOutcome.checkedRadioButtonId) {
                R.id.edit_plate_rb_open -> Outcome.OPEN
                R.id.edit_plate_rb_refuse -> Outcome.REFUSE
                else -> Outcome.NOTIFY
            }
            listener.onFinishedEditing(StoredPlate(plateId = storedPlate.plateId,name = name, plate = plate, outcome = outcome))
            dismiss()
        }

        return binding.root
    }
}
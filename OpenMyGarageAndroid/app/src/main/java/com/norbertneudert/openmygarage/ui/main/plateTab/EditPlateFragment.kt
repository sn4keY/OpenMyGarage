package com.norbertneudert.openmygarage.ui.main.plateTab

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.database.StoredPlate
import com.norbertneudert.openmygarage.databinding.EditPlateFragmentBinding
import java.util.*

class EditPlateFragment(var storedPlate: StoredPlate) : DialogFragment() {
    companion object {
        fun newInstance(storedPlate: StoredPlate) = EditPlateFragment(storedPlate)
    }

    interface EditPlateDialogListener {
        fun onFinishedEditing(storedPlate: StoredPlate, plateBefore: String = "")
    }

    private lateinit var binding: EditPlateFragmentBinding
    private var validName: Boolean = false
    private var validPlate: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.edit_plate_fragment, container, false)
        binding.editNameIt.setText(storedPlate.name)
        binding.editNameIt.validate("Adj meg egy nevet!", "name", binding.editDoneBtn) { s -> s.isNotEmpty()}
        binding.editPlateIt.setText(storedPlate.plate)
        binding.editPlateIt.validate("Rendszámot adj meg!", "plate", binding.editDoneBtn) { s -> s.isPlate() }
        binding.editPlateOutcome.check(when(storedPlate.outcome) {
            0 -> R.id.edit_plate_rb_open
            2 -> R.id.edit_plate_rb_refuse
            else -> R.id.edit_plate_rb_notify
        })
        binding.editDoneBtn.isEnabled = false
        binding.editDoneBtn.isClickable = false
        binding.editDoneBtn.setOnClickListener {
            val listener = targetFragment as EditPlateDialogListener
            val name = binding.editNameIt.text.toString()
            val plate = binding.editPlateIt.text.toString()
            val outcome = when(binding.editPlateOutcome.checkedRadioButtonId) {
                R.id.edit_plate_rb_open -> 0
                R.id.edit_plate_rb_refuse -> 2
                else -> 1
            }
            listener.onFinishedEditing(StoredPlate(plateId = storedPlate.plateId,name = name, plate = plate.toUpperCase(
                Locale.ENGLISH), outcome = outcome), storedPlate.plate)
            dismiss()
        }

        return binding.root
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                afterTextChanged.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    fun EditText.validate(message: String, source: String, button: Button, validator: (String) -> Boolean) {
        this.afterTextChanged {
            if (validator(it)) {
                this.error = null
                validateSource(source)
                checkValidationAndEnableButton(button)
            } else {
                this.error = message
            }
        }
        if (validator(this.text.toString())) {
            this.error = null
            validateSource(source)
            checkValidationAndEnableButton(button)
        } else {
            this.error = message
        }
    }

    fun validateSource(source: String) {
        when( source ) {
            "plate" -> validPlate = true
            else -> validName = true
        }
    }

    fun checkValidationAndEnableButton(button: Button) {
        if (validName && validPlate) {
            button.isClickable = true
            button.isEnabled = true
        }
    }

    fun String.isPlate() : Boolean = this.length == 7 && this[3] == '-'
}
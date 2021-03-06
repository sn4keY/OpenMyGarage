package com.norbertneudert.openmygarage.ui.main.plateTab

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.apiservice.ApiHandlerStoredPlates
import com.norbertneudert.openmygarage.database.OMGDatabase
import com.norbertneudert.openmygarage.database.StoredPlate
import com.norbertneudert.openmygarage.databinding.PlateTabFragmentBinding

class PlateTabFragment : Fragment(), EditPlateFragment.EditPlateDialogListener {

    companion object {
        fun newInstance() = PlateTabFragment()
    }

    private lateinit var viewModel: PlateTabViewModel
    private lateinit var binding: PlateTabFragmentBinding
    private lateinit var apiHandler: ApiHandlerStoredPlates

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.plate_tab_fragment, container, false)

        val activity = requireNotNull(this.activity)
        val application = activity.application
        val dataSource = OMGDatabase.getInstance(application).storedPlate
        apiHandler = ApiHandlerStoredPlates(dataSource)
        val viewModelFactory = PlateTabViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlateTabViewModel::class.java)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        val adapter = PlateAdapter(apiHandler, activity.supportFragmentManager, this)
        binding.plateList.adapter = adapter

        viewModel.plates.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.fabAdd.setOnClickListener {
            showEditor(activity)
        }

        return binding.root
    }

    override fun onFinishedEditing(storedPlate: StoredPlate, plateBefore: String) {
        Log.i("PlateTabFragment", "onFinishedEditing called")
        Log.i("PlateTabFragment", storedPlate.plateId.toString())
        //viewModel.onEdit(storedPlate)
        apiHandler.postStoredPlate(storedPlate, plateBefore)
    }

    private fun showEditor(activity: FragmentActivity){
        val editor = EditPlateFragment.newInstance(StoredPlate())
        editor.setTargetFragment(this,300)
        editor.show(activity.supportFragmentManager, "dialog")
    }
}

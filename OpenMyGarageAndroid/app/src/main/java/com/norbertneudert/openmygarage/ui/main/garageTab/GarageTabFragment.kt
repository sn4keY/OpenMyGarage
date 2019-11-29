package com.norbertneudert.openmygarage.ui.main.garageTab

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.norbertneudert.openmygarage.R

class GarageTabFragment : Fragment() {

    companion object {
        fun newInstance() = GarageTabFragment()
    }

    private lateinit var viewModel: GarageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.garagetab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GarageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

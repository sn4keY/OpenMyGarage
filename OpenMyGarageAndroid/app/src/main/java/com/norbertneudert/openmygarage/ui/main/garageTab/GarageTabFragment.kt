package com.norbertneudert.openmygarage.ui.main.garageTab

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.databinding.GaragetabFragmentBinding

class GarageTabFragment : Fragment() {

    companion object {
        fun newInstance() = GarageTabFragment()
    }

    private lateinit var viewModel: GarageViewModel
    private lateinit var binding: GaragetabFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.garagetab_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(GarageViewModel::class.java)

        binding.viewModel = viewModel
        binding.openButton.setOnClickListener {
            viewModel.toggleGarage()
        }

        return binding.root
    }
}

package com.norbertneudert.openmygarage.ui.main.platesTab

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.norbertneudert.openmygarage.R

class PlatesTabFragment : Fragment() {

    companion object {
        fun newInstance() = PlatesTabFragment()
    }

    private lateinit var viewModel: PlatesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.platestab_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlatesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

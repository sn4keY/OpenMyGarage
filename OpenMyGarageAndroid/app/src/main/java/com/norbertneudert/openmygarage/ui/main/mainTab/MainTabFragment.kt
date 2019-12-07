package com.norbertneudert.openmygarage.ui.main.mainTab

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.databinding.MainTabFragmentBinding

class MainTabFragment : Fragment() {

    companion object {
        fun newInstance() = MainTabFragment()
    }

    private lateinit var viewModel: MainTabViewModel
    private lateinit var binding: MainTabFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(MainTabViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.main_tab_fragment, container, false)
        binding.setLifecycleOwner(this)

        return binding.root
    }
}

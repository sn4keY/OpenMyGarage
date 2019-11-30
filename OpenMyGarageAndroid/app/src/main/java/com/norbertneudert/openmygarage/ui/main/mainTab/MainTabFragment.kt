package com.norbertneudert.openmygarage.ui.main.mainTab

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.databinding.MaintabFragmentBinding
import kotlinx.android.synthetic.main.maintab_fragment.*

class MainTabFragment : Fragment() {

    companion object {
        fun newInstance() = MainTabFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MaintabFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.maintab_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding.viewModel = viewModel

        return binding.root
    }
}

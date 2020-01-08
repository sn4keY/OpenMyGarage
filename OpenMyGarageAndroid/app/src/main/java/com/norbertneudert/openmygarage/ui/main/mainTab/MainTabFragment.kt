package com.norbertneudert.openmygarage.ui.main.mainTab

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.apiservice.ApiHandlerEntryLogs
import com.norbertneudert.openmygarage.database.OMGDatabase
import com.norbertneudert.openmygarage.databinding.MainTabFragmentBinding
import com.norbertneudert.openmygarage.ui.main.logTab.EntryLogAdapter

class MainTabFragment : Fragment() {

    companion object {
        fun newInstance() = MainTabFragment()
    }

    private lateinit var viewModel: MainTabViewModel
    private lateinit var binding: MainTabFragmentBinding
    private lateinit var apiHandler: ApiHandlerEntryLogs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_tab_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = OMGDatabase.getInstance(application).entryLog
        //apiHandler = ApiHandlerEntryLogs(dataSource)
        val viewModelFactory = MainTabViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainTabViewModel::class.java)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        val adapter = EntryLogAdapter()
        binding.mainRecyclerView.adapter = adapter

        viewModel.logs.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshDatabase()
        }

        binding.mainFAB.setOnClickListener {
            Toast.makeText(context, "Kapu nyitása / zárása !", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

    private fun refreshDatabase() {
        //apiHandler.onClear()
        //apiHandler.refreshDatabase()
        binding.swipeRefreshLayout.isRefreshing = false
    }
}

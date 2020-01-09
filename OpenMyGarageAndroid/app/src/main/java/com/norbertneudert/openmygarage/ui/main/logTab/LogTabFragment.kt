package com.norbertneudert.openmygarage.ui.main.logTab

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.norbertneudert.openmygarage.R
import com.norbertneudert.openmygarage.apiservice.ApiHandlerEntryLogs
import com.norbertneudert.openmygarage.database.OMGDatabase
import com.norbertneudert.openmygarage.databinding.LogTabFragmentBinding

class LogTabFragment : Fragment() {

    companion object {
        fun newInstance() = LogTabFragment()
    }

    private lateinit var viewModel: LogTabViewModel
    private lateinit var binding: LogTabFragmentBinding
    private lateinit var apiHandler: ApiHandlerEntryLogs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.log_tab_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = OMGDatabase.getInstance(application).entryLog
        apiHandler = ApiHandlerEntryLogs.getInstance(dataSource)
        val viewModelFactory = LogTabViewModelFactory(dataSource, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LogTabViewModel::class.java)

        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)

        val adapter = EntryLogAdapter()
        binding.logList.adapter = adapter

        viewModel.logs.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.refreshLayout.setOnRefreshListener {
            refreshDatabase()
        }

        return binding.root
    }

    private fun refreshDatabase() {
        binding.refreshLayout.isRefreshing = apiHandler.refreshDatabase()
    }
}

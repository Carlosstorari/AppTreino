package com.chscorp.apptreino.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.chscorp.apptreino.databinding.FragmentListTreinosBinding
import com.chscorp.apptreino.ui.viewModels.ListTreinosViewModel
import com.chscorp.apptreino.ui.adapter.ListTreinoAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListTreinosFragment : BaseFragment() {
    private lateinit var binding: FragmentListTreinosBinding
    private val navController by lazy { findNavController() }
    private val viewModel: ListTreinosViewModel by viewModel()
    private val adapter: ListTreinoAdapter by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListTreinosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchAll().observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                adapter.refresh(list)
                val rv = binding.rvListTreino
                adapter.onItemClickListener = { selectedTreino ->
                    selectedTreino.id?.let {
                        val directions =
                            ListTreinosFragmentDirections.actionHomeFragmentToTreinoFragment(
                                it
                            )
                        navController.navigate(directions)
                    }
                }
                rv.adapter = adapter
            }
        })
        binding.fab.setOnClickListener {
            val direction =
                ListTreinosFragmentDirections.actionHomeFragmentToCreateNewTreinoFragment()
            navController.navigate(direction)
        }
    }


}
package com.chscorp.apptreino.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.chscorp.apptreino.SampleListTreino
import com.chscorp.apptreino.databinding.FragmentHomeBinding
import com.chscorp.apptreino.ui.adapter.ListTreinoAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val navController by lazy { findNavController() }
    private val viewModel: ListTreinosViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.searchAll().observe(viewLifecycleOwner, Observer {
            it?.let { list ->
                val adapter = ListTreinoAdapter(context = requireActivity(), list)
                val rv = binding.rvListTreino
                rv.adapter = adapter
            }
        })
        binding.fab.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToCreateNewTreinoFragment()
            navController.navigate(direction)
        }
    }


}
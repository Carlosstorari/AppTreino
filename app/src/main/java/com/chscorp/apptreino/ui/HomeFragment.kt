package com.chscorp.apptreino.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.chscorp.apptreino.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToCreateNewTreinoFragment()
            navController.navigate(direction)
        }
    }



}
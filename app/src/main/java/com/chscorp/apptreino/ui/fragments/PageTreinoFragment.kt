package com.chscorp.apptreino.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chscorp.apptreino.R
import com.chscorp.apptreino.databinding.FragmentPageTreinoBinding
import com.chscorp.apptreino.ui.adapter.ListExercicioAdapter
import com.chscorp.apptreino.ui.viewModels.PageTreinoViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PageTreinoFragment() : BaseFragment() {
    private val navController by lazy {
        findNavController()
    }
    private val args by navArgs<PageTreinoFragmentArgs>()
    private val treinoId by lazy {
        args.treinoId
    }
    private val viewModel: PageTreinoViewModel by viewModel { parametersOf(treinoId) }
    private lateinit var binding: FragmentPageTreinoBinding
    private val adapter: ListExercicioAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPageTreinoBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()
        requireActivity().title = "Treino"
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_page_treino, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return setupMenu(menuItem)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        setupListExercicios()
        setupFabAddExercicio()

        return binding.root
    }

    private fun setupListExercicios() {
        viewModel.getListExercicios().observe(viewLifecycleOwner) {
            it?.let { list ->
                adapter.refresh(list)
                val rv = binding.rvListExercicio
                rv.adapter = adapter
            }
        }
    }
    private fun setupUpdateDelete() {
        viewModel.delete().observe(viewLifecycleOwner) {
            navController.popBackStack()
        }
    }
    private fun setupMenu(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.modify -> {
                setupUpdateTreino()
                true
            }

            R.id.delete -> {
                setupUpdateDelete()
                true
            }

            else -> false
        }
    }
    private fun setupUpdateTreino() {
        PageTreinoFragmentDirections
            .actionTreinoFragmentToCreateNewTreinoFragment(treinoId)
            .let(navController::navigate)
    }

    private fun setupFabAddExercicio(){
        binding.fabAddExercicios.setOnClickListener {
            PageTreinoFragmentDirections
                .actionTreinoFragmentToCreateNewExercicioFragment(treinoId)
                .let(navController::navigate)
        }
    }
}
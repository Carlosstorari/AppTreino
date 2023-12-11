package com.chscorp.apptreino.ui.fragments

import android.os.Bundle
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
import com.chscorp.apptreino.ui.viewModels.PageTreinoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PageTreinoFragment : BaseFragment() {
    private val navController by lazy {
        findNavController()
    }
    private val args by navArgs<PageTreinoFragmentArgs>()
    private val treinoId by lazy {
        args.treinoId
    }
    private val viewModel: PageTreinoViewModel by viewModel { parametersOf(treinoId) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_page_treino, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.modify -> {
                        PageTreinoFragmentDirections
                            .actionTreinoFragmentToCreateNewTreinoFragment(treinoId)
                            .let(navController::navigate)
                        true
                    }
                    R.id.delete -> {
                        viewModel.delete().observe(viewLifecycleOwner) {
                            navController.popBackStack()
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return inflater.inflate(R.layout.fragment_page_treino, container, false)
    }




}
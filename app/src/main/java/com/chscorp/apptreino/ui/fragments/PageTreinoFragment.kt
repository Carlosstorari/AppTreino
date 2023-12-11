package com.chscorp.apptreino.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
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


class PageTreinoFragment : BaseFragment() {
    private val navController by lazy {
        findNavController()
    }

    private val args by navArgs<PageTreinoFragmentArgs>()
    private val treinoId by lazy {
        args.treinoId
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return inflater.inflate(R.layout.fragment_page_treino, container, false)
    }




}
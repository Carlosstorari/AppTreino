package com.chscorp.apptreino.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.chscorp.apptreino.R
import com.chscorp.apptreino.databinding.FragmentCreateAccountBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateAccountFragment : Fragment() {

    private val controller by lazy {
        findNavController()
    }
    private lateinit var binding: FragmentCreateAccountBinding
    private val viewModel: CreateAccountViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirmRegisterUserBtn.setOnClickListener {
            val email = binding.userEmail.editText?.text.toString()
            val senha = binding.userPassword.editText?.text.toString()
            viewModel.registerNewUser(email, senha).observe(viewLifecycleOwner, Observer {
                it?.let { registerWithSuccess ->
                    if (registerWithSuccess) {
                        Snackbar.make(
                            view,
                            "Cadastro realizado com sucesso",
                            Snackbar.LENGTH_SHORT)
                            .show()
                        controller.popBackStack()
                    } else {
                        Snackbar.make(
                            view,
                            "Ocorreu uma falha no cadastro",
                            Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            })

        }
    }

}
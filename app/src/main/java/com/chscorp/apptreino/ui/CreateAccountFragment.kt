package com.chscorp.apptreino.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.chscorp.apptreino.databinding.FragmentCreateAccountBinding
import com.chscorp.apptreino.extensions.snackBar
import com.chscorp.apptreino.model.User
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
        setupBtnRegister()
    }

    private fun setupBtnRegister() {
        binding.apply {
            confirmRegisterUserBtn.setOnClickListener {

                cleanFields()

                val email = userEmail.editText?.text.toString()
                val password = userPassword.editText?.text.toString()
                val confirmPassword = confirmUserPassword.editText?.text.toString()

                val userRegisterValid = validateFields(email, password, confirmPassword)

                if (userRegisterValid) {
                    register(User(email, password))
                }


            }
        }
    }

    private fun register(user: User) {
        viewModel.registerNewUser(user.email, user.password).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                if (resource.content) {
                    view?.snackBar("Cadastro realizado com sucesso")
                    controller.popBackStack()
                } else {
                    val errorMessage = resource.error ?: "Ocorreu uma falha no cadastro"
                    view?.snackBar(errorMessage)

                }
            }
        })
    }

    private fun validateFields(
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var userRegisterValid = true
        if (email.isBlank()) {
            binding.userEmail.error = "E-mail é necessário"
            userRegisterValid = false
        }
        if (password.isBlank()) {
            binding.userPassword.error = "Senha é necessaria"
            userRegisterValid = false
        }
        if (confirmPassword.isBlank()) {
            binding.confirmUserPassword.error =
                "Confirmação da senha é necessaria"
            userRegisterValid = false
        }
        if (password != confirmPassword) {
            binding.confirmUserPassword.error =
                "Senhas diferentes"
            userRegisterValid = false
        }
        return userRegisterValid
    }

    private fun cleanFields() {
        binding.userEmail.error = null
        binding.userPassword.error = null
        binding.confirmUserPassword.error = null
    }

}
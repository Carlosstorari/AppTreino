package com.chscorp.apptreino.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.chscorp.apptreino.R
import com.chscorp.apptreino.databinding.FragmentLoginBinding
import com.chscorp.apptreino.extensions.snackBar
import com.chscorp.apptreino.model.User
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val controller by lazy {
        findNavController()
    }
    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginBtn()
        setupCreateAccountBtn()
    }

    private fun setupCreateAccountBtn() {
        binding.createAccountBtn.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToAccountFragment()
            controller.navigate(direction)
        }
    }

    private fun setupLoginBtn() {
        binding.apply {
            loginBtn.setOnClickListener {
                cleanFields()
                val email = loginEmail.editText?.text.toString()
                val password = loginPassword.editText?.text.toString()
                if (validateFields(email, password)) {
                    authLogin(email, password)
                }

            }
        }
    }

    private fun authLogin(email: String, password: String) {
        viewModel.authLogin(User(email, password))
            .observe(viewLifecycleOwner, Observer { it ->
                it?.let { resource ->
                    if (resource.content) {
                        val direction =
                            LoginFragmentDirections.acaoLoginParaListaProdutos()
                        controller.navigate(direction)
                    } else {
                        val errorMessage = resource.error?.let { error ->
                            getString(error)
                        } ?: getString(R.string.fail_auth)

                        view?.snackBar(errorMessage)
                    }
                }
            })
    }

    private fun FragmentLoginBinding.validateFields(
        email: String,
        password: String
    ): Boolean {
        var isLoginValid = true

        if (email.isBlank()) {
            loginEmail.error = getString(R.string.email_required)
            isLoginValid = false
        }

        if (password.isBlank()) {
            loginPassword.error = getString(R.string.password_required)
            isLoginValid = false
        }
        return isLoginValid
    }

    private fun cleanFields() {
        binding.loginEmail.error = null
        binding.loginPassword.error = null
    }
}
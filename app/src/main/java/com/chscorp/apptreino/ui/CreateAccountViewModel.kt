package com.chscorp.apptreino.ui

import androidx.lifecycle.ViewModel
import com.chscorp.apptreino.repository.FirebaseAuthRepository

class CreateAccountViewModel(private val repository: FirebaseAuthRepository): ViewModel() {

    fun registerNewUser(email: String, password: String) {
        repository.cadastraUsuario(email, password)
    }
}
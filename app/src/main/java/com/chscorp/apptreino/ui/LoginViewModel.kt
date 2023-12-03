package com.chscorp.apptreino.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chscorp.apptreino.model.User
import com.chscorp.apptreino.repository.FirebaseAuthRepository
import com.chscorp.apptreino.repository.Resource

class LoginViewModel(private val fireBaseAuthRepository: FirebaseAuthRepository): ViewModel() {
    fun authLogin(user: User): LiveData<Resource<Boolean>> {
        return fireBaseAuthRepository.authLogin(user)
    }

    fun desloga() {
        fireBaseAuthRepository.logout()
    }

    fun estaLogado(): Boolean = fireBaseAuthRepository.isLoged()

    fun naoEstaLogado(): Boolean = !estaLogado()
}
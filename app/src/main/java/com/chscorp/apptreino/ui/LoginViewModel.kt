package com.chscorp.apptreino.ui

import androidx.lifecycle.ViewModel
import com.chscorp.apptreino.repository.FirebaseAuthRepository

class LoginViewModel(private val fireBaseAuthRepository: FirebaseAuthRepository): ViewModel() {
    fun loga(){
        //repository.loga()
    }

    fun desloga() {
        fireBaseAuthRepository.desloga()
    }

    fun estaLogado(): Boolean = fireBaseAuthRepository.estaLogado()

    fun naoEstaLogado(): Boolean = !estaLogado()
}
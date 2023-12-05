package com.chscorp.apptreino.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chscorp.apptreino.repository.FirebaseAuthRepository
import com.chscorp.apptreino.repository.Resource

class CreateAccountViewModel(private val repository: FirebaseAuthRepository) : ViewModel() {

    fun registerNewUser(email: String, password: String): LiveData<Resource<Boolean>> =
        repository.createNewUser(email, password)

}
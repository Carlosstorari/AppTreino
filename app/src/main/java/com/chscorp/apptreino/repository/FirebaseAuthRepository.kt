package com.chscorp.apptreino.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception
import java.lang.IllegalArgumentException

private const val TAG = "firebaseAuthRepository"

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {
    private fun desloga(firebaseAuth: FirebaseAuth) {
        firebaseAuth.signOut()
    }

    private fun verificaSeUsuarioEstaLogado(firebaseAuth: FirebaseAuth) {
        val usuarioFirebase: FirebaseUser? = firebaseAuth.currentUser
        if (usuarioFirebase != null) {

        } else {

        }
    }

    private fun autenticaUsuario(firebaseAuth: FirebaseAuth) {
        firebaseAuth.signInWithEmailAndPassword("carlos.storari95@gmail.com", "teste1234")
            .addOnSuccessListener {

            }.addOnFailureListener {
                Log.e("Errorrr", "onCreate", it)

            }
    }

    fun createNewUser(email: String, password: String): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            val task =
                firebaseAuth.createUserWithEmailAndPassword(email, password)
            task.addOnSuccessListener {
                Log.i(TAG, "cadastra: cadastro sucedido")
                liveData.value = Resource(true)
            }
            task.addOnFailureListener { exception ->
                Log.e(TAG, "cadastro: cadastro falhou", exception)

                val errorMessage: String = catchRegisterError(exception)

                liveData.value = Resource(false, errorMessage)
            }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, "E-mail ou senha não pode ser vazio")
        }

        return liveData
    }

    private fun catchRegisterError(exception: Exception): String {
        val errorMessage: String = when (exception) {
            is FirebaseAuthWeakPasswordException -> "Senha deve ter mais de 6 digitos"
            is FirebaseAuthInvalidCredentialsException -> "E-mail invalido"
            is FirebaseAuthUserCollisionException -> "E-mail já cadastrado"
            else -> "Erro desconhecido"
        }
        return errorMessage
    }
}
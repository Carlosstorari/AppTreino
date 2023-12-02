package com.chscorp.apptreino.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

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

    fun createNewUser(email: String, password: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val task =
            firebaseAuth.createUserWithEmailAndPassword(email, password)
        task.addOnSuccessListener {
            Log.i(TAG, "cadastra: cadastro sucedido")
            liveData.value = true
        }
        task.addOnFailureListener {
            Log.e(TAG, "cadastro: cadastro falhou", it)
            liveData.value = false
        }
        return liveData
    }
}
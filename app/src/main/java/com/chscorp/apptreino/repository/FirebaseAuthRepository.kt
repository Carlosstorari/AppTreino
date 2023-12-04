package com.chscorp.apptreino.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chscorp.apptreino.R
import com.chscorp.apptreino.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser

private const val TAG = "firebaseAuthRepository"

class FirebaseAuthRepository(private val firebaseAuth: FirebaseAuth) {
    fun logout() {
        firebaseAuth.signOut()
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

                val errorMessage: Int = catchRegisterError(exception)

                liveData.value = Resource(false, errorMessage)
            }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, R.string.e_mail_or_password_empty)
        }

        return liveData
    }

    private fun catchRegisterError(exception: Exception): Int = when (exception) {
            is FirebaseAuthWeakPasswordException -> R.string.password_must_be_more_characters
            is FirebaseAuthInvalidCredentialsException -> R.string.invalid_email
            is FirebaseAuthUserCollisionException -> R.string.email_already_registered
            else -> R.string.unknown_error


    }

    fun isLoged(): Boolean {
        val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
        if (firebaseUser != null) {
            return true
        }
        return false
    }

    fun authLogin(user: User): LiveData<Resource<Boolean>> {
        val liveData = MutableLiveData<Resource<Boolean>>()
        try {
            firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        liveData.value = Resource(true)
                    } else {
                        Log.e(TAG, "auth: ", task.exception)
                        val errorMessage = catchAuthError(task.exception)
                        liveData.value = Resource(false, errorMessage)
                    }
                }
        } catch (e: IllegalArgumentException) {
            liveData.value = Resource(false, R.string.e_mail_or_password_empty)
        }
        return liveData
    }

    private fun catchAuthError(exception: Exception?): Int = when (exception) {
        is FirebaseAuthInvalidUserException,
        is FirebaseAuthInvalidCredentialsException -> R.string.email_or_senha_incorrect
        else -> R.string.unknown_error
    }
}
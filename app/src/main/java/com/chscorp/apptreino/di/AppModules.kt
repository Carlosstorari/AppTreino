package com.chscorp.apptreino.di

import com.chscorp.apptreino.repository.FirebaseAuthRepository
import com.chscorp.apptreino.ui.CreateAccountFragment
import com.chscorp.apptreino.ui.CreateAccountViewModel
import com.chscorp.apptreino.ui.HomeFragment
import com.chscorp.apptreino.ui.LoginFragment
import com.chscorp.apptreino.ui.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { CreateAccountViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}

val uiModules = module {
    factory { LoginFragment() }
    factory { HomeFragment() }
    factory { CreateAccountFragment() }
}

val repositoryModule = module {
    single { FirebaseAuthRepository(get()) }
}

val firebaseModule = module {
    single <FirebaseAuth>{ Firebase.auth }
}

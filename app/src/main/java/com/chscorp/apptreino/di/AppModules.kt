package com.chscorp.apptreino.di

import com.chscorp.apptreino.repository.FirebaseAuthRepository
import com.chscorp.apptreino.repository.TreinoRepository
import com.chscorp.apptreino.ui.CreateAccountFragment
import com.chscorp.apptreino.ui.CreateAccountViewModel
import com.chscorp.apptreino.ui.CreateNewTreinoViewModel
import com.chscorp.apptreino.ui.HomeFragment
import com.chscorp.apptreino.ui.ListTreinosViewModel
import com.chscorp.apptreino.ui.LoginFragment
import com.chscorp.apptreino.ui.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { CreateAccountViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ListTreinosViewModel(get()) }
    viewModel { CreateNewTreinoViewModel(get()) }
}

val uiModules = module {
    factory { LoginFragment() }
    factory { HomeFragment() }
    factory { CreateAccountFragment() }
}

val repositoryModule = module {
    single { FirebaseAuthRepository(get()) }
    single { TreinoRepository(get()) }
}

val firebaseModule = module {
    single <FirebaseAuth>{ Firebase.auth }
    single <FirebaseFirestore>{ Firebase.firestore }
}

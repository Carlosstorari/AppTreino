package com.chscorp.apptreino.di

import com.chscorp.apptreino.repository.ExercicioRepository
import com.chscorp.apptreino.repository.FirebaseAuthRepository
import com.chscorp.apptreino.repository.TreinoRepository
import com.chscorp.apptreino.ui.adapter.ListExercicioAdapter
import com.chscorp.apptreino.ui.fragments.CreateAccountFragment
import com.chscorp.apptreino.ui.viewModels.CreateAccountViewModel
import com.chscorp.apptreino.ui.viewModels.CreateNewTreinoViewModel
import com.chscorp.apptreino.ui.fragments.ListTreinosFragment
import com.chscorp.apptreino.ui.viewModels.ListTreinosViewModel
import com.chscorp.apptreino.ui.fragments.LoginFragment
import com.chscorp.apptreino.ui.viewModels.LoginViewModel
import com.chscorp.apptreino.ui.adapter.ListTreinoAdapter
import com.chscorp.apptreino.ui.fragments.CreateNewExercicioFragment
import com.chscorp.apptreino.ui.fragments.PageTreinoFragment
import com.chscorp.apptreino.ui.viewModels.CreateNewExercicioViewModel
import com.chscorp.apptreino.ui.viewModels.PageTreinoViewModel
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
    viewModel<PageTreinoViewModel> { (id: String) -> PageTreinoViewModel(id, get(), get()) }
    viewModel { CreateNewExercicioViewModel(get()) }
}

val uiModules = module {
    factory { LoginFragment() }
    factory { ListTreinosFragment() }
    factory { CreateAccountFragment() }
    factory { PageTreinoFragment() }
    factory { ListTreinoAdapter(get()) }
    factory { ListExercicioAdapter(get()) }
    factory { CreateNewExercicioFragment() }
}

val repositoryModule = module {
    single { FirebaseAuthRepository(get()) }
    single { TreinoRepository(get()) }
    single { ExercicioRepository(get()) }
}

val firebaseModule = module {
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }
}

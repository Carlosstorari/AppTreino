package com.chscorp

import android.app.Application
import com.chscorp.apptreino.di.firebaseModule
import com.chscorp.apptreino.di.repositoryModule
import com.chscorp.apptreino.di.uiModules
import com.chscorp.apptreino.di.viewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(uiModules, viewModels, repositoryModule, firebaseModule))
        }

    }
}
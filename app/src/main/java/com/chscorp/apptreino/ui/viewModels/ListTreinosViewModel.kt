package com.chscorp.apptreino.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chscorp.apptreino.model.Treino
import com.chscorp.apptreino.repository.TreinoRepository

class ListTreinosViewModel(private val repository: TreinoRepository): ViewModel() {
    fun searchAll(): LiveData<List<Treino>> = repository.searchTreino()

}
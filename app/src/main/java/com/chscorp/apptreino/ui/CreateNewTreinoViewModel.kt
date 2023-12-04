package com.chscorp.apptreino.ui

import androidx.lifecycle.ViewModel
import com.chscorp.apptreino.model.Treino
import com.chscorp.apptreino.repository.TreinoRepository

class CreateNewTreinoViewModel(private val repository: TreinoRepository): ViewModel() {
    fun save(treino: Treino) = repository.saveTreino(treino)

}

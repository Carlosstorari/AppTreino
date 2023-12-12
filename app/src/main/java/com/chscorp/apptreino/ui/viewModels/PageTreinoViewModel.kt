package com.chscorp.apptreino.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chscorp.apptreino.model.Exercicio
import com.chscorp.apptreino.repository.ExercicioRepository
import com.chscorp.apptreino.repository.TreinoRepository

class PageTreinoViewModel(
    private val treinoId: String,
    private val repository: TreinoRepository,
    private val repositoryExercicio: ExercicioRepository
): ViewModel() {
    fun delete(): LiveData<Boolean> {
        return repository.delete(treinoId)
    }

    fun getListExercicios(): LiveData<List<Exercicio>> {
        return repositoryExercicio.searchTreino(treinoId)
    }

    val foundedTreino = repository.searchForId(treinoId)
}
package com.chscorp.apptreino.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chscorp.apptreino.model.Exercicio
import com.chscorp.apptreino.repository.ExercicioRepository

class CreateNewExercicioViewModel(private val repository: ExercicioRepository): ViewModel() {
    fun saveExercicio(exercicio: Exercicio): LiveData<Boolean> {
        return repository.saveExercicio(exercicio)
    }
}
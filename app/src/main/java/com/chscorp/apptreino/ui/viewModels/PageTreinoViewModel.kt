package com.chscorp.apptreino.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chscorp.apptreino.repository.TreinoRepository

class PageTreinoViewModel(
    private val treinoId: String,
    private val repository: TreinoRepository
): ViewModel() {
    fun delete(): LiveData<Boolean> {
        return repository.delete(treinoId)
    }

    val foundedTreino = repository.searchForId(treinoId)
}
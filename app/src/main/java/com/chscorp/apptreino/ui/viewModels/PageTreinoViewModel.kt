package com.chscorp.apptreino.ui.viewModels

import androidx.lifecycle.ViewModel
import com.chscorp.apptreino.repository.TreinoRepository

class PageTreinoViewModel(
    treinoId: String,
    repository: TreinoRepository
): ViewModel() {

    val foundedTreino = repository.searchForId(treinoId)
}
package com.chscorp.apptreino.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chscorp.apptreino.model.Exercicio
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class ExercicioRepository(private val firestore: FirebaseFirestore) {

    fun searchTreino(treinoId: String): LiveData<List<Exercicio>> = MutableLiveData<List<Exercicio>>().apply {
        firestore.collection(COLLECTION_FIRESTORE_EXERCICIO)
            .addSnapshotListener { s, _ ->
                s?.let { snapshot ->
                    val exercicios: List<Exercicio> = snapshot.documents.mapNotNull { doc ->
                        doc.toObject<ExercicioDocument>()?.toExercicio(doc.id)
                    }.filter { it.treinoId == treinoId }

                    value = exercicios
                }
            }
    }

    private class ExercicioDocument(
        val nome: Long = 0,
        val obcervacoes: String = "",
        val treinoId: String = ""
    ){
        fun toExercicio(id: String) = Exercicio(
            exercicioId = id,
            nome = nome,
            observacoes = obcervacoes,
            treinoId = treinoId,
            imagem = ""
        )
    }

    companion object {
        private const val COLLECTION_FIRESTORE_EXERCICIO = "Exercicio"
    }
}
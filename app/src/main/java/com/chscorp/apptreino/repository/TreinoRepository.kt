package com.chscorp.apptreino.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chscorp.apptreino.model.Treino
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class TreinoRepository(private val firestore: FirebaseFirestore) {
    fun saveTreino(treino: Treino): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val treinoMapeado = mapOf<String, Any?>(
            FIELD_NOME to treino.name,
            FIELD_DESCRICAO to treino.description,
            FIELD_DATE to treino.date
        )
        firestore.collection(COLLECTION_FIRESTORE_TREINO)
            .add(treinoMapeado)
            .addOnSuccessListener {
                liveData.value = true
            }
            .addOnFailureListener {
                liveData.value = false
            }
        return liveData
    }

    fun searchTreino(): LiveData<List<Treino>> {
        val liveData: MutableLiveData<List<Treino>> = MutableLiveData<List<Treino>>()
        firestore.collection(COLLECTION_FIRESTORE_TREINO)
            .get()
            .addOnSuccessListener {
                it?.let { snapshot ->
                    val treinos = mutableListOf<Treino>()
                    for (treino in snapshot.documents) {
                        treino.data?.let { content ->
                            val nome = content[FIELD_NOME] as Long
                            val desc = content[FIELD_DESCRICAO] as String
                            val date = content[FIELD_DATE] as Timestamp?
                            treinos.add(Treino(nome, desc, date))
                        }
                    }
                    liveData.value = treinos
                }
            }
        return liveData
    }

    companion object {
        private const val COLLECTION_FIRESTORE_TREINO = "Treino"
        private const val FIELD_NOME = "nome"
        private const val FIELD_DESCRICAO = "descricao"
        private const val FIELD_DATE = "date"
    }

}
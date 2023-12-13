package com.chscorp.apptreino.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chscorp.apptreino.model.Treino
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import java.util.Calendar

class TreinoRepository(private val firestore: FirebaseFirestore) {

    fun searchForId(id: String): LiveData<Treino> = MutableLiveData<Treino>().apply {
        firestore.collection(COLLECTION_FIRESTORE_TREINO)
            .document(id)
            .addSnapshotListener { s, _ ->
            s?.let { snapshot ->
                snapshot.toObject<TreinoDocument>()?.toTreino(snapshot.id)
                    ?.let { treino ->
                        value = treino
                    }
            }
        }
    }

    fun saveTreino(treino: Treino) =  MutableLiveData<Boolean>().apply {
        val treinoDocument = TreinoDocument(
            nome = treino.name, descricao = treino.description, date = treino.date
        )

        val collection = firestore.collection(COLLECTION_FIRESTORE_TREINO)
        val document = treino.id?.let { id ->
            collection.document(id)
        } ?: collection.document()

        document.set(treinoDocument)

        value = true
    }
    fun searchTreino(): LiveData<List<Treino>> = MutableLiveData<List<Treino>>().apply {
        firestore.collection(COLLECTION_FIRESTORE_TREINO)
            .addSnapshotListener { s, _ ->
                s?.let { snapshot ->
                    val treinos: List<Treino> = snapshot.documents.mapNotNull { doc ->
                        doc.toObject<TreinoDocument>()?.toTreino(doc.id)
                    }
                    value = treinos
                }
            }
    }

    fun delete(treinoId: String): LiveData<Boolean> = MutableLiveData<Boolean>().apply {
        firestore.collection(COLLECTION_FIRESTORE_TREINO)
            .document(treinoId)
            .delete()

        value = true
    }


    private class TreinoDocument(
        val nome: Long = 0,
        val descricao: String = "",
        val date: Timestamp? = Timestamp(
            Calendar.getInstance().time
        )
    ) {
        fun toTreino(id: String): Treino {
            return Treino(
                id = id,
                name = nome,
                description = descricao,
                date = date
            )
        }

    }

    companion object {
        private const val COLLECTION_FIRESTORE_TREINO = "Treino"
    }

}
package com.chscorp.apptreino.model

data class Exercicio (
    val exercicioId: String? = null,
    val treinoId: String? = "",
    val imagem: String? = "",
    val nome: Long = 0,
    val observacoes: String = ""
)
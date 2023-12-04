package com.chscorp.apptreino.model

import com.google.firebase.Timestamp
import java.util.Calendar

data class Treino(
    val name: Long = 1, val description: String = "teste", val date: Timestamp? = Timestamp(
        Calendar.getInstance().time
    )
)
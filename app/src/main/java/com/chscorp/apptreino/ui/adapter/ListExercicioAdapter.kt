package com.chscorp.apptreino.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chscorp.apptreino.databinding.ExercicioItemBinding
import com.chscorp.apptreino.model.Exercicio

class ListExercicioAdapter(
    private val context: Context,
    exercicios: List<Exercicio> = emptyList()
) : RecyclerView.Adapter<ListExercicioAdapter.ViewHolder>() {

    private val exercicios = exercicios.toMutableList()

    inner class ViewHolder(private val binding: ExercicioItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var exercicio: Exercicio

        fun bind(exercicio: Exercicio) {
            this.exercicio = exercicio
            val name = binding.exercicioName
            val obcervacoes = binding.exercicioObservacao
            name.text = exercicio.nome.toString()
            obcervacoes.text = exercicio.observacoes
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ExercicioItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = exercicios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercicios = exercicios[position]
        holder.bind(exercicios)
    }

    fun refresh(exercicio: List<Exercicio>) {
        this.exercicios.clear()
        this.exercicios.addAll(exercicio)
        notifyDataSetChanged()
    }
}
package com.chscorp.apptreino.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chscorp.apptreino.databinding.TreinoItemBinding
import com.chscorp.apptreino.model.Treino
import java.text.SimpleDateFormat
import java.util.Date

class ListTreinoAdapter(
    private val context: Context,
    treinos: List<Treino> = emptyList()
) : RecyclerView.Adapter<ListTreinoAdapter.ViewHolder>() {
    private val treinos = treinos.toMutableList()

    inner class ViewHolder(private val binding: TreinoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var treino: Treino

        fun bind(treino: Treino) {
            this.treino = treino
            val name = binding.treinoName
            name.text = treino.name.toString()
            val description = binding.treinoDesc
            description.text = treino.description
            val date = binding.treinoDate
            treino.date?.let {
                val milliseconds = it.seconds * 1000 + it.nanoseconds / 1000000
                val sdf = SimpleDateFormat("dd/MM/yyyy")
                val netDate = Date(milliseconds)
                date.text  = sdf.format(netDate).toString()
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = TreinoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = treinos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val treinos = treinos[position]
        holder.bind(treinos)
    }

    fun refresh(produtos: List<Treino>) {
        this.treinos.clear()
        this.treinos.addAll(produtos)
        notifyDataSetChanged()
    }
}
package com.chscorp.apptreino.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chscorp.apptreino.R
import com.chscorp.apptreino.databinding.FragmentCreateNewExercicioBinding
import com.chscorp.apptreino.extensions.snackBar
import com.chscorp.apptreino.model.Exercicio
import com.chscorp.apptreino.ui.viewModels.CreateNewExercicioViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateNewExercicioFragment : BaseFragment() {

    private lateinit var binding: FragmentCreateNewExercicioBinding
    private val args by navArgs<CreateNewExercicioFragmentArgs>()
    private val treinoId by lazy {
        args.treinoId
    }
    private val viewModel: CreateNewExercicioViewModel by viewModel()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewExercicioBinding.inflate(inflater, container, false)

        binding.confirmRegisterUserBtn.setOnClickListener {
           val name =  binding.exercicioName.editText?.text.toString()
           val observacoes =  binding.exercicioObservacao.editText?.text.toString()
            viewModel.saveExercicio(
                Exercicio(nome = name.toLong(), observacoes = observacoes, treinoId = treinoId)
            ).observe(viewLifecycleOwner) {
                it?.let { saved ->
                    if (saved) {
                        navController.popBackStack()
                        return@observe
                    }
                    view?.snackBar(getString(R.string.fail_save_exercicio))
                }
            }
        }
        return binding.root
    }

}
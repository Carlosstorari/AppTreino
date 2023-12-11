package com.chscorp.apptreino.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chscorp.apptreino.R
import com.chscorp.apptreino.databinding.FragmentCreateNewTreinoBinding
import com.chscorp.apptreino.extensions.snackBar
import com.chscorp.apptreino.model.Treino
import com.chscorp.apptreino.ui.viewModels.CreateNewTreinoViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class CreateNewTreinoFragment : BaseFragment() {
    private lateinit var binding: FragmentCreateNewTreinoBinding
    private val viewModel: CreateNewTreinoViewModel by viewModel()
    private val args by navArgs<CreateNewTreinoFragmentArgs>()
    private val treinoId by lazy {
        args.treinoId
    }
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewTreinoBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        treinoId?.let { id ->
            viewModel.searchForId(id).observe(viewLifecycleOwner) {
                it?.let {treino ->
                    val name = treino.name
                    val description = treino.description
                    val date = treino.date
                    binding.apply {
                        treinoName.editText?.setText(name.toString())
                        treinoDesc.editText?.setText(description.toString())
                        treinoDate.editText?.setText(timestampToDateTimeFormater(timestampToMilli(date?.seconds)))
                        requireActivity().title = "Alterar dados do treino"
                    }

                }
            }
        }


        var datepickerValue: Timestamp? = null
        binding.treinoDateEditText.setOnClickListener {
            val dateSelected = openMaterialDatePicker()
            dateSelected
                .addOnPositiveButtonClickListener { dataEmMilisegundos ->
                    datepickerValue = Timestamp(Date(dataEmMilisegundos))
                    val dateFormatted = timestampToDateTimeFormater(dataEmMilisegundos)
                    binding.treinoDateEditText.setText(dateFormatted)
                }
        }
        binding.confirmRegisterUserBtn.setOnClickListener {
            setupRegisterTreinoBtn(datepickerValue)
        }
    }
    fun timestampToMilli(time: Long?): Long {
        if (time != null) {
            return if (time < 1000000000000L) {
                time * 1000
            } else {
                time
            }
        }
        return 0
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun timestampToDateTimeFormater(dataEmMilisegundos: Long): String? {
        val date = getDate(dataEmMilisegundos)
        return dateTimeFormatter()?.format(date)
    }

    private fun setupRegisterTreinoBtn(datepickerValue: Timestamp?) {
        val name = binding.treinoName.editText?.text.toString()
        val description = binding.treinoDesc.editText?.text.toString()
        val treino = Treino(
            id = treinoId,
            name = name.toLong(),
            description = description,
            date = datepickerValue
        )
        viewModel.save(treino).observe(viewLifecycleOwner) {
            it?.let { saved ->
                if (saved) {
                    navController.popBackStack()
                    return@observe
                }
                view?.snackBar(getString(R.string.fail_save_treino))
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateTimeFormatter(): DateTimeFormatter? {
        return DateTimeFormatter
            .ofPattern("dd/MM/yyyy", Locale("pt-br"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(dateInMilliseconds: Long): LocalDate? {
        return Instant.ofEpochMilli(dateInMilliseconds)
            .atZone(ZoneId.of("America/Sao_Paulo"))
            .withZoneSameInstant(ZoneId.ofOffset("UTC", ZoneOffset.UTC))
            .toLocalDate()
    }

    private fun openMaterialDatePicker(): MaterialDatePicker<Long> {
        val dateSelected = MaterialDatePicker
            .Builder.datePicker().build()
        dateSelected.show(requireActivity().supportFragmentManager, "MATERIAL_DATE_PICKER")
        return dateSelected
    }


}
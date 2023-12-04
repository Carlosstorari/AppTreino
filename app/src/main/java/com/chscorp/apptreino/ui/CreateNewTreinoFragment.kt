package com.chscorp.apptreino.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.chscorp.apptreino.R
import com.chscorp.apptreino.databinding.FragmentCreateNewTreinoBinding
import com.chscorp.apptreino.extensions.snackBar
import com.chscorp.apptreino.model.Treino
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.IllegalArgumentException


class CreateNewTreinoFragment : BaseFragment() {
    private lateinit var binding: FragmentCreateNewTreinoBinding
    private val viewModel: CreateNewTreinoViewModel by viewModel()
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
        var datepickerValue: Timestamp? = null
        binding.treinoDateEditText.setOnClickListener {
            val dateSelected = openMaterialDatePicker()
            dateSelected
                .addOnPositiveButtonClickListener { dataEmMilisegundos ->
                    val date = getDate(dataEmMilisegundos)
                    datepickerValue = Timestamp(Date(dataEmMilisegundos))
                    val formatter = dateTimeFormatter()
                    val dateFormatted = formatter?.format(date)
                    binding.treinoDateEditText.setText(dateFormatted)
                }
        }
        binding.confirmRegisterUserBtn.setOnClickListener {
            setupRegisterTreinoBtn(datepickerValue)
        }
    }

    private fun setupRegisterTreinoBtn(datepickerValue: Timestamp?) {
        val name = binding.treinoName.editText?.text.toString()
        val description = binding.treinoDesc.editText?.text.toString()
        val treino = Treino(name.toLong(), description, datepickerValue)
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

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun setupDatePicker(datepickerValue: Timestamp?) {
//        var datepickerValue1 = datepickerValue
//        val dateSelected = openMaterialDatePicker()
//        dateSelected
//            .addOnPositiveButtonClickListener { dataEmMilisegundos ->
//                val date = getDate(dataEmMilisegundos)
//                datepickerValue1 = Timestamp(Date(dataEmMilisegundos))
//                val formatter = dateTimeFormatter()
//                val dateFormatted = formatter?.format(date)
//                binding.treinoDateEditText.setText(dateFormatted)
//            }
//    }

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
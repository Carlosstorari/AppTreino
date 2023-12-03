package com.chscorp.apptreino.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.chscorp.apptreino.databinding.FragmentCreateNewTreinoBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

class CreateNewTreinoFragment : BaseFragment(){
    private lateinit var binding: FragmentCreateNewTreinoBinding
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
        binding.treinoDateEditText.setOnClickListener {
            val dateSelected = openMaterialDatePicker()
            dateSelected
                .addOnPositiveButtonClickListener { dataEmMilisegundos ->
                    val date = getDate(dataEmMilisegundos)
                    val formatter = dateTimeFormatter()
                    val dateFormatted = formatter?.format(date)
                    binding.treinoDateEditText.setText(dateFormatted)
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
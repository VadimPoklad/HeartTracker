package com.example.pme.act.layouts

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pme.add.DataTimeManager
import com.example.pme.add.DatabaseHelper
import com.example.pme.add.Record
import com.example.pme.databinding.FragmentAddBinding

class AddFragment : Fragment(), OnDateSetListener, OnTimeSetListener {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val dataTimeManager = DataTimeManager()
    private lateinit var db:DatabaseHelper
    private val maxPulse = 250
    private val maxSystolic = 200
    private val maxDiastolic = 200
    private val defaultPulse = 50
    private val defaultSystolic = 100
    private val defaultDiastolic = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddBinding.inflate(inflater, container, false)

        db = DatabaseHelper.getInstance(requireContext())
        init()
        setListeners()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init(){
        binding.numberPickerSystolic.maxValue = maxSystolic
        binding.numberPickerDiastolic.maxValue = maxDiastolic
        binding.numberPickerPulse.maxValue = maxPulse
        binding.numberPickerPulse.value = defaultPulse
        binding.numberPickerDiastolic.value = defaultDiastolic
        binding.numberPickerSystolic.value = defaultSystolic

        binding.addTimeText.text= dataTimeManager.getCurrentTime()
        binding.addDateText.text= dataTimeManager.getCurrentDate()
    }


    private fun setListeners(){
        binding.addDateText.setOnClickListener {
            DatePickerDialog(requireContext(), this, dataTimeManager.year,  dataTimeManager.month,  dataTimeManager.day).show()
        }

        binding.addTimeText.setOnClickListener {
            TimePickerDialog(requireContext(), this,  dataTimeManager.hour,  dataTimeManager.minute, true).show()
        }

        binding.addSave.setOnClickListener {
            val systolic = binding.numberPickerSystolic.value
            val diastolic = binding.numberPickerDiastolic.value
            val pulse = binding.numberPickerPulse.value
            val record = Record(null, dataTimeManager.getCurrentDate(), dataTimeManager.getCurrentTime(), systolic, diastolic, pulse)
            db.insert(record)
            Toast.makeText(context, "New record added", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dataTimeManager.year=year
        dataTimeManager.month=month
        dataTimeManager.day=dayOfMonth
        binding.addDateText.text=dataTimeManager.getCurrentDate()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        dataTimeManager.hour=hourOfDay
        dataTimeManager.minute=minute
        binding.addTimeText.text=dataTimeManager.getCurrentTime()
    }
}
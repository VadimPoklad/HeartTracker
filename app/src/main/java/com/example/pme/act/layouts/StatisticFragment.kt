package com.example.pme.act.layouts

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.pme.R
import com.example.pme.add.DatabaseHelper
import com.example.pme.databinding.FragmentStatisticBinding


class StatisticFragment : Fragment() {
    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        db = DatabaseHelper.getInstance(requireContext())
        fillTable(db.getStatistic().toNestedStringList())
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fillTable(rowData: List<List<String>>){
        for(row in rowData){
            val tableRow = TableRow(requireContext())
            val layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            tableRow.layoutParams = layoutParams

            for (data in row) {
                val textView = TextView(requireContext())
                textView.text = data
                textView.setPadding(5, 20, 5, 20)
                val textParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                textView.gravity = Gravity.CENTER
                textView.textSize= 20F
                textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text))
                textView.layoutParams = textParams
                tableRow.addView(textView)
            }

            binding.statTable.addView(tableRow)
        }
    }
}
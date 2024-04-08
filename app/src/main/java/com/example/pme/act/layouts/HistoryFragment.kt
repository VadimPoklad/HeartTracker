package com.example.pme.act.layouts


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pme.act.ItemDecoration
import com.example.pme.act.RecordAdapter
import com.example.pme.add.DatabaseHelper
import com.example.pme.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var db:DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init(){
        db = DatabaseHelper.getInstance(requireContext())
        binding.histRecordsView.layoutManager = LinearLayoutManager(context)
        binding.histRecordsView.adapter =  RecordAdapter(db)
        binding.histRecordsView.layoutManager = LinearLayoutManager(context)
        binding.histRecordsView.addItemDecoration(ItemDecoration())
    }
}
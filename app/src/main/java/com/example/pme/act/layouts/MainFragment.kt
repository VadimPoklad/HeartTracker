package com.example.pme.act.layouts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pme.R
import com.example.pme.act.ItemDecoration
import com.example.pme.act.RecordAdapter
import com.example.pme.add.DatabaseHelper
import com.example.pme.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    private lateinit var db:DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        init()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNew.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_AddFragment)
        }

        binding.allHistory.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_historyFragment)
        }

        binding.statistic.setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_StatisticFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init(){
        db = DatabaseHelper.getInstance(requireContext())
        binding.preview.layoutManager = LinearLayoutManager(context)
        binding.preview.adapter = RecordAdapter(db, 3)
        binding.preview.layoutManager = LinearLayoutManager(context)
        binding.preview.addItemDecoration(ItemDecoration())
    }
}
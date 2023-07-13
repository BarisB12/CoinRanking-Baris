package com.example.coinranking_baris.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinranking_baris.coins.CoinsAdapter
import com.example.coinranking_baris.coins.CoinsViewModel
import com.example.coinranking_baris.databinding.FragmentCoinBinding

class CoinsFragment : Fragment() {
    private lateinit var binding: FragmentCoinBinding
    private lateinit var adapter: CoinsAdapter
    private lateinit var recyclerView: RecyclerView

    private val viewModel: CoinsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinBinding.inflate(inflater)
        recyclerView = binding.recyclerView
        adapter = CoinsAdapter()
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        viewModel.coinList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }
}
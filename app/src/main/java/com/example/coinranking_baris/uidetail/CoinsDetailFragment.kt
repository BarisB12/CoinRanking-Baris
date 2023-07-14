package com.example.coinranking_baris.uidetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.coinranking_baris.databinding.FragmentDetailCoinsBinding
import com.example.coinranking_baris.detailcoins.CoinsDetailAdapter
import com.example.coinranking_baris.detailcoins.CoinsDetailViewModel

class CoinsDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailCoinsBinding
    private lateinit var adapter: CoinsDetailAdapter
    private lateinit var recyclerView: RecyclerView
    private val args by navArgs<CoinsDetailFragmentArgs>()

    private val viewModel: CoinsDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailCoinsBinding.inflate(inflater)
        recyclerView = binding.recyclerViewDetail

        adapter = CoinsDetailAdapter(listOf(args.coin))

        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        return binding.root
    }
}
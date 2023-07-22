package com.example.coinranking_baris.uidetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coinranking_baris.R

import com.example.coinranking_baris.databinding.FragmentDetailCoinsBinding
import com.example.coinranking_baris.detailcoins.CoinsHistoryViewModel
import com.example.coinranking_baris.detailcoins.LineChartAdapter
import com.example.coinranking_baris.model.Coin
import java.util.*

class CoinsDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailCoinsBinding
    private val args by navArgs<CoinsDetailFragmentArgs>()
    private lateinit var adapter: LineChartAdapter
    private lateinit var recyclerView: RecyclerView

    private val viewModel: CoinsHistoryViewModel by viewModels()

    private lateinit var selectedCoin: Coin
    private var isClicked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailCoinsBinding.inflate(inflater, container, false)
        selectedCoin = args.coin

        recyclerView = binding.recyclerViewLineChart
        adapter = LineChartAdapter()

        val detailPrice = "%.5f".format(Locale.ENGLISH, selectedCoin.price.toDouble())
        binding.textViewDetailPrice.text = "$${detailPrice}"
        binding.textMarketDetailCap.text = selectedCoin.change+"%"
        binding.textDetailName.text = selectedCoin.name
        binding.textViewDetailSymbol.text = selectedCoin.symbol

        val sparklineCoin = selectedCoin.sparkline

        if (sparklineCoin.isNotEmpty()) {
            val highPrice = sparklineCoin.maxOrNull() ?: 0.0
            val lowPrice = sparklineCoin.minOrNull() ?: 0.0
            binding.textHighDetailPrice.text = "$highPrice"
            binding.textLowDetailPrice.text = "$lowPrice"
        }

        val changeCoin = selectedCoin.change.toDouble()

        if (changeCoin > 0) {
            binding.textMarketDetailCap.setTextColor(ContextCompat.getColor(binding.root.context, R.color.green))
        } else {
            binding.textMarketDetailCap.setTextColor(ContextCompat.getColor(binding.root.context, R.color.red))
        }

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        viewModel.historyCoins.observe(viewLifecycleOwner) { historyList ->
            Log.d("CoinsDetailFragment", "History list: $historyList")
            adapter.submitList(historyList)
        }

        binding.imageViewBell.setOnClickListener {
            isClicked = !isClicked

            if (isClicked) {
                binding.imageViewBell.setImageResource(R.drawable.bell_clicked)
            } else {
                binding.imageViewBell.setImageResource(R.drawable.bell)
            }
        }

        binding.imageViewBack.isClickable
        binding.imageViewBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }
        return binding.root
    }
}
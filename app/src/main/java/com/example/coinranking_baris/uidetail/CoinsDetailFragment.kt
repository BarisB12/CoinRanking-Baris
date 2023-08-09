package com.example.coinranking_baris.uidetail

import android.graphics.Color
import android.os.Bundle
 import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.coinranking_baris.R
import com.example.coinranking_baris.databinding.FragmentDetailCoinsBinding
import com.example.coinranking_baris.detailcoins.CoinsDetailViewModel
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.model.HistoryX
import com.example.coinranking_baris.utils.loadImageFromUrl
import com.example.coinranking_baris.utils.orZero
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CoinsDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailCoinsBinding
    private val args by navArgs<CoinsDetailFragmentArgs>()
    private val viewModel: CoinsDetailViewModel by viewModels()

    private lateinit var selectedCoin: Coin
    private var isClicked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailCoinsBinding.inflate(inflater, container, false)
        selectedCoin = args.coin

        val detailPrice = "%.2f".format(Locale.ENGLISH, selectedCoin.price.toDouble())
        binding.textViewDetailPrice.text = "$${detailPrice}"
        binding.textDetailName.text = selectedCoin.name
        binding.textViewDetailChange.text = selectedCoin.change + "%"
        binding.textViewNo.text = selectedCoin.rank.toString()
        binding.textViewHVolume.text = selectedCoin.hVolume
        binding.textViewMarketCap.text = selectedCoin.marketCap
        binding.detailButtonRound.loadImageFromUrl(selectedCoin.iconUrl)
        val sparklineCoin = selectedCoin.sparkline

        if (sparklineCoin.isNotEmpty()) {
            val highPrice = sparklineCoin.maxOrNull() ?: 0.0.orZero()
            val lowPrice = sparklineCoin.minOrNull() ?: 0.0.orZero()
            binding.textHighDetailPrice.text = "$highPrice"
            binding.textLowDetailPrice.text = "$lowPrice"
        }

        val changeCoin = selectedCoin.change.toDouble()

        if (changeCoin > 0) {
            binding.textViewDetailChange.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.green
                )
            )
        } else {
            binding.textViewDetailChange.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.red
                )
            )
        }

        (viewModel.detailCoins.value)
        binding.imageViewBell.setOnClickListener {
            isClicked = !isClicked

            if (isClicked) {
                binding.imageViewBell.setImageResource(R.drawable.bell_clicked)
            } else {
                binding.imageViewBell.setImageResource(R.drawable.bell)
            }
        }

        binding.imageViewBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        return binding.root
    }
}
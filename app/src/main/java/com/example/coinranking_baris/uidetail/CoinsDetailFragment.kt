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
import com.example.coinranking_baris.detailcoins.CoinsHistoryViewModel
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.model.History
import com.example.coinranking_baris.model.HistoryX
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.text.SimpleDateFormat
import java.util.*

class CoinsDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailCoinsBinding
    private val args by navArgs<CoinsDetailFragmentArgs>()
    private lateinit var lineChart: LineChart
    private val viewModel: CoinsHistoryViewModel by viewModels()

    private lateinit var selectedCoin: Coin
    private var isClicked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailCoinsBinding.inflate(inflater, container, false)
        selectedCoin = args.coin

        lineChart = binding.lineChart

        val detailPrice = "%.5f".format(Locale.ENGLISH, selectedCoin.price.toDouble())
        binding.textViewDetailPrice.text = "$${detailPrice}"
        binding.textMarketDetailCap.text = selectedCoin.change + "%"
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
            binding.textMarketDetailCap.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.green
                )
            )
        } else {
            binding.textMarketDetailCap.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.red
                )
            )
        }

        viewModel.historyCoins.observe(viewLifecycleOwner) { historyList ->
            updateLineChart(historyList)
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

    private fun updateLineChart(historyList: List<HistoryX>) {
        val entries = ArrayList<Entry>()

        for (data in historyList) {
            val price = data.price.toFloat()
            val timestamp = data.timestamp.toFloat()
            entries.add(Entry(timestamp, price))
        }

        val dataSet = LineDataSet(entries, "Price History")
        dataSet.color = ColorTemplate.getHoloBlue()
        dataSet.setDrawCircles(true)
        dataSet.setDrawCircleHole(false)
        dataSet.setCircleColor(Color.RED)
        dataSet.circleRadius = 4f
        dataSet.lineWidth = 2f
        dataSet.setDrawValues(true)
        dataSet.valueTextColor = Color.BLACK
        dataSet.mode = LineDataSet.Mode.LINEAR

        val lineDataSets = ArrayList<ILineDataSet>()
        lineDataSets.add(dataSet)

        val lineData = LineData(lineDataSets)

        lineChart.data = lineData
        lineChart.xAxis.granularity = 2f
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.animateXY(3000, 3000)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.labelCount = 2
        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            private val dateFormat = SimpleDateFormat("dd/MM HH:mm", Locale.ENGLISH)
            override fun getFormattedValue(value: Float): String {
                val millis = (value * 1000L).toLong()
                return dateFormat.format(Date(millis))
            }
        }
        lineChart.invalidate()
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.axisMaximum = entries.size.toFloat() - 1
        lineChart.extraRightOffset = 30f

        val yAxisLeft: YAxis = lineChart.axisLeft
        yAxisLeft.setDrawGridLines(true)
    }
}
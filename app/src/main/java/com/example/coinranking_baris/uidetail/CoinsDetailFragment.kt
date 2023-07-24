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

class CoinsDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailCoinsBinding
    private val args by navArgs<CoinsDetailFragmentArgs>()
    private lateinit var lineChart: LineChart
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
            viewModel.selectedCoin.value.let {
                updateLineChart(historyList)
            }
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
        var counter = 1f
        val maxVisibleLabels = 6

        val labelCount = minOf(historyList.size, maxVisibleLabels)
        lineChart.xAxis.labelCount = labelCount

        for (data in historyList) {
            val price = data.price.toFloat()
            entries.add(Entry(counter, price))
            counter += 1f
        }
        val dataSet = LineDataSet(entries, "Price History")
        dataSet.color = Color.BLUE
        dataSet.setDrawCircles(false)
        dataSet.lineWidth = 2f
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.BLUE
        dataSet.fillAlpha = 70
        dataSet.setDrawValues(false)
        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

        val lineDataSets = ArrayList<ILineDataSet>()
        lineDataSets.add(dataSet)

        val lineData = LineData(lineDataSets)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.textSize = 8f

        val yAxis: YAxis = lineChart.axisLeft
        yAxis.textSize = 10f

        lineChart.data = lineData
        lineChart.xAxis.granularity = 2f
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.valueFormatter = object : ValueFormatter() {
            private val dateFormat = SimpleDateFormat("dd/MM HH:mm", Locale.ENGLISH)
            override fun getFormattedValue(value: Float): String {
                val millis = (historyList[value.toInt()].timestamp * 1000L)
                return dateFormat.format(Date(millis))
            }
        }
        lineChart.invalidate()
        lineChart.axisRight.isEnabled = false
        lineChart.xAxis.axisMaximum = entries.size.toFloat()
        lineChart.extraRightOffset = 30f

        val yAxisLeft: YAxis = lineChart.axisLeft
        yAxisLeft.setDrawGridLines(true)

        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false
    }
}
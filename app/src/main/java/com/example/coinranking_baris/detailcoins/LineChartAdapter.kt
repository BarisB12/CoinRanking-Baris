package com.example.coinranking_baris.detailcoins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinranking_baris.databinding.FragmentDetailItemBinding
import com.example.coinranking_baris.model.HistoryX
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate

class LineChartAdapter(private var historyList: List<HistoryX> = listOf()) : RecyclerView.Adapter<LineChartAdapter.HistoryCardHolder>() {

    inner class HistoryCardHolder(binding: FragmentDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: FragmentDetailItemBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryCardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FragmentDetailItemBinding = FragmentDetailItemBinding.inflate(inflater, parent, false)
        return HistoryCardHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryCardHolder, position: Int) {
        val history = historyList[position]
         val item = holder.binding

        val price = history.price.toFloat()
        val timestamp = history.timestamp.toFloat()

        val entry = Entry(timestamp, price)
        val dataSet = LineDataSet(listOf(entry), "Count")
        dataSet.color = ColorTemplate.getHoloBlue()
        dataSet.setDrawCircles(false)
        dataSet.lineWidth = 1f
        dataSet.setDrawValues(false)

        val lineDataSets = ArrayList<ILineDataSet>()
        lineDataSets.add(dataSet)

        item.lineChart.data = LineData(lineDataSets)
        item.lineChart.xAxis.granularity = 1f
        item.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        item.lineChart.xAxis.setDrawGridLines(false)
        item.lineChart.xAxis.labelCount = 3
        item.lineChart.axisRight.isEnabled = false
        item.lineChart.extraRightOffset = 30f

        val yAxisLeft: YAxis = item.lineChart.axisLeft
        yAxisLeft.setDrawGridLines(true)

        item.lineChart.invalidate()
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun submitList(updatedList: List<HistoryX>) {
        this.historyList = updatedList

        notifyDataSetChanged()
    }
}
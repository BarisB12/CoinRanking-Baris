package com.example.coinranking_baris.detailcoins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coinranking_baris.databinding.FragmentDetailItemBinding
import com.example.coinranking_baris.model.Coin

class CoinsDetailAdapter(
    private var coinDetailList: List<Coin> = listOf()
) : RecyclerView.Adapter<CoinsDetailAdapter.CoinsDetailCardHolder>() {

    inner class CoinsDetailCardHolder(binding: FragmentDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: FragmentDetailItemBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsDetailCardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FragmentDetailItemBinding = FragmentDetailItemBinding.inflate(inflater, parent, false)
        val context = parent.context
        return CoinsDetailCardHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinsDetailAdapter.CoinsDetailCardHolder, position: Int) {
        val item = holder.binding
        val detailCoin = coinDetailList[position]

        item.textViewDetailPrice.text = detailCoin.price
        item.textLowDetailPrice.text = detailCoin.lowVolume.toString()
        item.textHighDetailPrice.text = detailCoin.price
        item.textMarketDetailCap.text = detailCoin.marketCap
        item.textDetailName.text = detailCoin.name
        item.textViewDetailSymbol.text = detailCoin.symbol

    }

    override fun getItemCount(): Int {
        return coinDetailList.size
    }

    fun submitList(coinList: List<Coin>) {
        this.coinDetailList = coinList

        notifyDataSetChanged()
    }
}
package com.example.coinranking_baris.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coinranking_baris.databinding.CoinsItemBinding
import com.example.coinranking_baris.model.Coin

class CoinsAdapter(
    private var coinsList: List<Coin> = listOf()
) : RecyclerView.Adapter<CoinsAdapter.CoinsCardHolder>() {

    inner class CoinsCardHolder(binding: CoinsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: CoinsItemBinding

        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsCardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: CoinsItemBinding = CoinsItemBinding.inflate(inflater, parent, false)

        return CoinsCardHolder(binding)
    }

    override fun getItemCount(): Int {
        return coinsList.size
    }

    override fun onBindViewHolder(holder: CoinsCardHolder, position: Int) {
        val item = holder.binding
        val coin = coinsList[position]

        item.textViewCoin.text = coin.name
        item.textViewsymbol.text = coin.symbol
        item.textPrice.text = coin.price
        item.textMarketCap.text = coin.marketCap

        Glide.with(item.buttonRound)
            .load(coin.iconUrl)
            .into(item.buttonRound)
    }

    fun submitList(doctorList: List<Coin>) {
        this.coinsList = doctorList as ArrayList<Coin>

        notifyDataSetChanged()
    }
}
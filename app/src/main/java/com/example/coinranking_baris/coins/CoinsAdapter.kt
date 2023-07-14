package com.example.coinranking_baris.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coinranking_baris.databinding.CoinsItemBinding
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.model.Coins
import com.example.coinranking_baris.ui.CoinsFragmentDirections

class CoinsAdapter(
    private var coinList: List<Coin> = listOf()
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
        val context = parent.context
        return CoinsCardHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinsCardHolder, position: Int) {
        val item = holder.binding
        val coin = coinList[position]

        item.textViewCoin.text = coin.name
        item.textViewSymbol.text = coin.symbol
        item.textPrice.text = coin.price
        item.textMarketCap.text = coin.marketCap

        Glide.with(item.buttonRound)
            .load(coin.iconUrl)
            .into(item.buttonRound)

        item.root.setOnClickListener {
            val direction = CoinsFragmentDirections.actionCoinsFragmentToDetailCoinsFragment(coin)
            Navigation.findNavController(it).navigate(direction)
        }
    }
    override fun getItemCount(): Int {
        return coinList.size
    }

    fun submitList(coinList: List<Coin>) {
        this.coinList = coinList

        notifyDataSetChanged()
    }
}
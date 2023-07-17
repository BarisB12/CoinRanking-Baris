package com.example.coinranking_baris.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.coinranking_baris.R
import com.example.coinranking_baris.databinding.CoinsItemBinding
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.ui.CoinsFragmentDirections
import com.example.coinranking_baris.utils.loadImageFromUrl
import java.util.*

class CoinsAdapter(
    private var coinList: List<Coin> = listOf(),

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

    override fun onBindViewHolder(holder: CoinsCardHolder, position: Int) {
        val item = holder.binding
        val coin = coinList[position]

        item.textViewCoin.text = coin.name
        item.textViewSymbol.text = coin.symbol
        val price = "%.5f".format(Locale.ENGLISH, coin.price.toDouble())
        item.textPrice.text = "$$price"
        item.textMarketCap.text = coin.change+"%"
        item.buttonRound.loadImageFromUrl(coin.iconUrl)

        val changeCoin = coin.change.toDouble()
        if (changeCoin > 0) {
            item.textMarketCap.setTextColor(ContextCompat.getColor(item.root.context, R.color.green))
        } else {
            item.textMarketCap.setTextColor(ContextCompat.getColor(item.root.context, R.color.red))
        }

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
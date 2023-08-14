package com.example.coinranking_baris.coins

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.coinranking_baris.R
import com.example.coinranking_baris.databinding.CoinsItemBinding
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.sharedprefs.SharedPrefs
import com.example.coinranking_baris.ui.CoinsFragmentDirections
import com.example.coinranking_baris.utils.loadImageFromUrl
import java.util.*

class CoinsAdapter(
    private var coinList: List<Coin> = listOf()
) : RecyclerView.Adapter<CoinsAdapter.CoinsCardHolder>() {

    inner class CoinsCardHolder(val binding: CoinsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinsCardHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CoinsItemBinding.inflate(inflater, parent, false)
        return CoinsCardHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinsCardHolder, position: Int) {
        val coin = coinList[position]
        val item = holder.binding

        item.textViewCoin.text = coin.name
        item.textViewSymbol.text = coin.symbol
        val price = "%.3f".format(Locale.ENGLISH, coin.price.toDouble())
        item.textPrice.text = "$$price"
        item.textMarketCap.text = coin.change + "%"
        item.buttonRound.loadImageFromUrl(coin.iconUrl)

        val changeCoin = coin.change.toDouble()
        if (changeCoin > 0) {
            item.textMarketCap.setTextColor(ContextCompat.getColor(item.root.context, R.color.green))
        } else {
            item.textMarketCap.setTextColor(ContextCompat.getColor(item.root.context, R.color.red))
        }

        val isFavorite = SharedPrefs.checkFavoritedCoin(coin.name)
        updateFavoriteView(item.detailImageViewStar, isFavorite)

        item.detailImageViewStar.setOnClickListener {
            val updatedIsFavorite = !isFavorite
            updateFavoriteStatus(coin.name, updatedIsFavorite)
            updateFavoriteView(item.detailImageViewStar, updatedIsFavorite)
        }

        item.root.setOnClickListener {
            val direction = CoinsFragmentDirections.actionCoinsFragmentToDetailCoinsFragment(coin)
            Navigation.findNavController(it).navigate(direction)
        }
    }

    private fun updateFavoriteView(imageView: ImageView, isFavorite: Boolean) {
        if (isFavorite) {
            imageView.setColorFilter(ContextCompat.getColor(imageView.context, R.color.gold))
        } else {
            imageView.setColorFilter(ContextCompat.getColor(imageView.context, R.color.black))
        }
    }

    private fun updateFavoriteStatus(coinName: String, isFavorite: Boolean) {
        if (isFavorite) {
            SharedPrefs.addFavoritedCoin(coinName)
        } else {
            SharedPrefs.removeFavoritedCoin(coinName)
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
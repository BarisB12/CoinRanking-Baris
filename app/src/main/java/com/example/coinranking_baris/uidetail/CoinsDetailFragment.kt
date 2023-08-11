package com.example.coinranking_baris.uidetail

import android.content.Context
import android.graphics.Color
import android.os.Bundle
 import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.coinranking_baris.R
import com.example.coinranking_baris.databinding.FragmentDetailCoinsBinding
import com.example.coinranking_baris.detailcoins.CoinsDetailViewModel
import com.example.coinranking_baris.model.Coin
import com.example.coinranking_baris.sharedprefs.SharedPrefs
import com.example.coinranking_baris.utils.loadImageFromUrl
import com.example.coinranking_baris.utils.orZero
import java.util.*

class CoinsDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailCoinsBinding
    private val args by navArgs<CoinsDetailFragmentArgs>()
    private val viewModel: CoinsDetailViewModel by viewModels()

    private lateinit var selectedCoin: Coin

    private var isFavorite: Boolean = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetail(selectedCoin.uuid)

    }

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
        binding.textViewNo.text = "NO."+ selectedCoin.rank.toString()
        binding.textViewHVolume.text = selectedCoin.hVolume
        binding.textViewMarketCap.text = selectedCoin.marketCap
        binding.detailButtonRound.loadImageFromUrl(selectedCoin.iconUrl)
        binding.textViewSymbol.text = selectedCoin.symbol
        binding.textViewUuid.text = selectedCoin.uuid
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
        loadFavoriteStatus()
        viewModel.coinDetailLiveData.observe(viewLifecycleOwner) {

        }

        binding.imageViewStar.setOnClickListener {
            val isFavorite = SharedPrefs.checkFavoritedCoin(selectedCoin.name)
            saveFavoriteStatus(!isFavorite)
            loadFavoriteStatus()
        }

        binding.imageViewBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        return binding.root
    }

    private fun loadFavoriteStatus() {
        val isFavorite = SharedPrefs.checkFavoritedCoin(selectedCoin.name)
        setFavColor(isFavorite, binding.imageViewStar, requireContext())
    }

    private fun saveFavoriteStatus(isFavorite: Boolean) {
        if (isFavorite) {
            SharedPrefs.addFavoritedCoin(selectedCoin.name)
        } else {
            SharedPrefs.removeFavoritedCoin(selectedCoin.name)
        }
    }

    private fun setFavColor(isFavorite: Boolean, imageView: ImageView, context: Context) {
        if (isFavorite) {
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.gold))
        } else {
            imageView.setColorFilter(ContextCompat.getColor(context, R.color.black))
        }
    }
}
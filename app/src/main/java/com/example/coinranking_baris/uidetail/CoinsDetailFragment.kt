package com.example.coinranking_baris.uidetail

import android.content.Context
import androidx.lifecycle.Observer
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
import com.example.coinranking_baris.model.CoinDetailResponse
import com.example.coinranking_baris.sharedprefs.SharedPrefs
import com.example.coinranking_baris.utils.loadImageFromUrl
import java.util.*

class CoinsDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailCoinsBinding
    private val args by navArgs<CoinsDetailFragmentArgs>()
    private val viewModel: CoinsDetailViewModel by viewModels()

    private lateinit var selectedCoin: Coin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetail(args.coin.uuid)

        viewModel.coinDetailLiveData.observe(viewLifecycleOwner, Observer { coinDetail ->
            coinDetail?.let {
                bindCoinDetail(coinDetail)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailCoinsBinding.inflate(inflater, container, false)
        selectedCoin = args.coin


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

    private fun bindCoinDetail(coinDetail: CoinDetailResponse.Data.CoinDetail) {
        val detailPrice = coinDetail.price?.let { "%.2f".format(Locale.ENGLISH, it.toDouble()) }
        binding.textViewDetailPrice.text = "$${detailPrice}"
        binding.textDetailName.text = coinDetail.name
        binding.textViewDetailChange.text = coinDetail.change + "%"
        binding.textViewNo.text = "NO."+ coinDetail.rank.toString()
        binding.textViewHVolume.text = coinDetail.hVolume
        binding.textViewMarketCap.text = coinDetail.marketCap
        coinDetail.iconUrl?.let { binding.detailButtonRound.loadImageFromUrl(it) }
        binding.textViewSymbol.text = coinDetail.symbol
        binding.textViewUuid.text = coinDetail.uuid

        val sparklineCoin = selectedCoin.sparkline
        if (sparklineCoin.isNotEmpty()) {
            val highPrice = sparklineCoin.maxOrNull() ?: 0.0
            val lowPrice = sparklineCoin.minOrNull() ?: 0.0
            binding.textHighDetailPrice.text = "$highPrice"
            binding.textLowDetailPrice.text = "$lowPrice"
        }
    }
}
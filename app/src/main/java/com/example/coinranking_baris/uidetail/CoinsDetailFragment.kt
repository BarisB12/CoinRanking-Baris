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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val args by navArgs<CoinsDetailFragmentArgs>()
    private val viewModel: CoinsDetailViewModel by viewModels()

    private lateinit var selectedCoin: Coin

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDetail(args.coin.uuid)
        binding.detailShimmerLayout.startShimmer()

        viewModel.coinDetailLiveData.observe(viewLifecycleOwner, Observer { coinDetail ->
            coinDetail?.let {
                bindCoinDetail(coinDetail)
                binding.detailShimmerLayout.stopShimmer()
                binding.detailShimmerLayout.visibility = View.GONE
                binding.cardViewDetail.visibility = View.VISIBLE
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
        swipeRefreshLayout = binding.swipeRefreshLayout

        binding.imageViewBack.setOnClickListener {
            Navigation.findNavController(binding.root).navigateUp()
        }

        return binding.root
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

    private fun formatLargeNumber(number: Double): String {
        val units = arrayOf("K", "M", "B", "T")
        var formattedNumber = number
        var unitIndex = 0

        while (formattedNumber >= 1000 && unitIndex < units.size - 1) {
            formattedNumber /= 1000
            unitIndex++
        }

        return "%.2f %s".format(Locale.ENGLISH, formattedNumber, units[unitIndex])
    }

    private fun bindCoinDetail(coinDetail: CoinDetailResponse.Data.CoinDetail) {
        val detailPrice = coinDetail.price?.let { "%.2f".format(Locale.ENGLISH, it.toDouble()) }
        binding.textViewDetailPrice.text = "$${detailPrice}"
        binding.textDetailName.text = coinDetail.name
        binding.textViewDetailChange.text = coinDetail.change + "%"
        binding.textViewNo.text = "NO." + coinDetail.rank.toString()
        coinDetail.iconUrl?.let { binding.detailButtonRound.loadImageFromUrl(it) }
        binding.textViewSymbol.text = coinDetail.symbol
        binding.textViewUuid.text = coinDetail.uuid


        coinDetail.marketCap?.toDoubleOrNull()?.let { marketCap ->
            val formattedMarketCap = formatLargeNumber(marketCap)
            binding.textViewMarketCap.text = formattedMarketCap
        }

        coinDetail.hVolume?.toDoubleOrNull()?.let { hVolume ->
            val formattedHVolume = formatLargeNumber(hVolume)
            binding.textViewHVolume.text = formattedHVolume
        }

        val isFavorite = coinDetail.isFav
//            coinDetail.name?.let {
//            SharedPrefs.checkFavoritedCoin(it)
//        }
        if (isFavorite != null) {
            setFavColor(isFavorite, binding.imageViewStar, requireContext())

            binding.imageViewStar.setOnClickListener {
                val isFavorite = coinDetail.name?.let { SharedPrefs.checkFavoritedCoin(it) }
                if (isFavorite != null) {
                    val updatedIsFavorite = !isFavorite
                    setFavColor(updatedIsFavorite, binding.imageViewStar, requireContext())
                    saveFavoriteStatus(updatedIsFavorite)
                }
            }
        }
        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getDetail(args.coin.uuid)
            swipeRefreshLayout.isRefreshing = false
        }

        val sparklineCoin = coinDetail.sparkline

        if (sparklineCoin != null) {
            val prices = sparklineCoin.mapNotNull { it?.toDoubleOrNull() }

            if (prices.isNotEmpty()) {
                var highPrice = Double.MIN_VALUE
                var lowPrice = Double.MAX_VALUE

                for (price in prices) {
                    if (price > highPrice) {
                        highPrice = price
                    }
                    if (price < lowPrice) {
                        lowPrice = price
                    }
                }
                binding.textHighDetailPrice.text = "$%.3f".format(highPrice)
                binding.textLowDetailPrice.text = "$%.3f".format(lowPrice)
            }
        }

        val changeCoin = coinDetail.change?.toDouble()

        if (changeCoin != null) {
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
        }
    }
}
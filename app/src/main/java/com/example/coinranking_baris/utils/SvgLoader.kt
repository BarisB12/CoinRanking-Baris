package com.example.coinranking_baris.utils

import android.graphics.Insets.add
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.coinranking_baris.R

fun ImageView.loadImageFromUrl(imageUrl: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadImageFromUrl.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(600)
        .placeholder(R.drawable.round)
        .error(R.drawable.backspace_image)
        .data(imageUrl)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}
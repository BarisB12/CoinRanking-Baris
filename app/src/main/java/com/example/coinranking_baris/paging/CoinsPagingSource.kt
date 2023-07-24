package com.example.coinranking_baris.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.coinranking_baris.api.CoinsService
import com.example.coinranking_baris.model.Coin


class CoinsPagingSource(private val coinService: CoinsService) : PagingSource<Int, Coin>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Coin> {
        try {
            val pageNumber = params.key ?: 1
            val pageSize = 25

            val response = coinService.getAllCoinsWithPage(page = pageNumber, pageSize = pageSize)
            val coins = response.data.coin

            return LoadResult.Page(
                data = coins,
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (coins.isNotEmpty()) pageNumber + 1 else null
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Coin>): Int? {
        if (state.anchorPosition == null || state.anchorPosition == 0) {
            return null
        }

        val anchorPage = state.closestPageToPosition(state.anchorPosition!!)
        return anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
}
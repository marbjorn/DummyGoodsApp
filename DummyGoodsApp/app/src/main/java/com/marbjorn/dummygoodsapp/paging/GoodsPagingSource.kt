package com.marbjorn.dummygoodsapp.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.marbjorn.dummygoodsapp.GoodsModel
import com.marbjorn.dummygoodsapp.network.GoodsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext

class GoodsPagingSource(private val repository: GoodsRepository) : PagingSource<Int, GoodsModel>() {
    override fun getRefreshKey(state: PagingState<Int, GoodsModel>): Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GoodsModel> {
        return try {
            withContext(Dispatchers.IO + SupervisorJob()) {
                val currentPage = params.key ?: 0
                val data = repository.getGoodsList(currentPage)
                LoadResult.Page(
                    data = data,
                    prevKey = if (currentPage == 0) null else currentPage.minus(1),
                    nextKey = if (data.isEmpty()) null else currentPage.plus(1)
                )
            }
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}
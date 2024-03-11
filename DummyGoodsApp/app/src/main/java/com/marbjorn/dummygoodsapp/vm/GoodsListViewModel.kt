package com.marbjorn.dummygoodsapp.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.marbjorn.dummygoodsapp.data.GoodsRepository
import com.marbjorn.dummygoodsapp.paging.GoodsPagingSource

class GoodsListViewModel(private val repository: GoodsRepository) : ViewModel() {
    val goodsList = Pager(PagingConfig(pageSize = 2)) {
        GoodsPagingSource(repository)
    }.flow.cachedIn(viewModelScope)
}

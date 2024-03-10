package com.marbjorn.dummygoodsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.marbjorn.dummygoodsapp.network.GoodsRepository
import com.marbjorn.dummygoodsapp.paging.GoodsPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GoodsListViewModel(/*val repository: GoodsRepository*/) : ViewModel() {
    val goodsList = Pager(PagingConfig(pageSize = 1)) {
        GoodsPagingSource(repository)
    }.flow.cachedIn(viewModelScope)

    companion object {
        val repository = GoodsRepository()
    }
}
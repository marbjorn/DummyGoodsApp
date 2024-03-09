package com.marbjorn.dummygoodsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class GoodsListViewModel : ViewModel() {
    private val goodsDao : GoodsDao = GoodsDaoImpl()
    private var goodsList_ : MutableLiveData<List<GoodsModel>> = MutableLiveData(emptyList())
    val goodsList : LiveData<List<GoodsModel>> = goodsList_
    fun updateGoodsList(skip : Int, limit : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            goodsList_.postValue(goodsDao.getGoods(skip, limit))
        }
    }
}
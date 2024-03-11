package com.marbjorn.dummygoodsapp.data

interface GoodsRepository {
    suspend fun getGoodsList(page: Int): List<GoodsModel>
    suspend fun getSingleGoods(id: Int): GoodsModel
}

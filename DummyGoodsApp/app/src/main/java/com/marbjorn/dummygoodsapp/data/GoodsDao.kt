package com.marbjorn.dummygoodsapp.data

interface GoodsDao {
    suspend fun getAllGoods(startPos: Int, size: Int): List<GoodsModel>
    suspend fun getSingleGoods(id: Int): GoodsModel
}

package com.marbjorn.dummygoodsapp

interface GoodsDao {
    suspend fun getGoods(startPos : Int, batchSize : Int): List<GoodsModel>
}
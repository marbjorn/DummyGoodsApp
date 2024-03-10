package com.marbjorn.dummygoodsapp.network

import com.marbjorn.dummygoodsapp.GoodsModel

interface GoodsDao {
    suspend fun getGoods(startPos : Int, batchSize : Int): List<GoodsModel>
}
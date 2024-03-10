package com.marbjorn.dummygoodsapp.network

import com.marbjorn.dummygoodsapp.GoodsModel

interface GoodsDao {
    suspend fun getAllGoods(startPos : Int, size : Int): List<GoodsModel>
    suspend fun getSingleGoods(id : Int): GoodsModel
}
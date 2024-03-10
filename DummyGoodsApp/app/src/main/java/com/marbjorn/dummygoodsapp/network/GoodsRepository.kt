package com.marbjorn.dummygoodsapp.network

class GoodsRepository {
    private val goodsDao : GoodsDao = GoodsDaoImpl()
    suspend fun getGoodsList(page : Int) =
        goodsDao.getAllGoods(page * PAGE_SIZE, PAGE_SIZE)

    suspend fun getSingleGoods(id : Int) = goodsDao.getSingleGoods(id)

    companion object {
        private const val PAGE_SIZE = 30
    }
}
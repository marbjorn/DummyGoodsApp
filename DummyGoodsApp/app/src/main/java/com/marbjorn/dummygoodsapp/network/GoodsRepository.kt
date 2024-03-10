package com.marbjorn.dummygoodsapp.network

class GoodsRepository {
    private val goodsDao : GoodsDao = GoodsDaoImpl()
    suspend fun getGoodsList(page : Int) =
        goodsDao.getGoods(page * PAGE_SIZE, PAGE_SIZE)

    companion object {
        private const val PAGE_SIZE = 30
    }
}
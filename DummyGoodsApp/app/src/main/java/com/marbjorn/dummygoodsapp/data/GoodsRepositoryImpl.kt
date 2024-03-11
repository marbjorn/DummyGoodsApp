package com.marbjorn.dummygoodsapp.data

class GoodsRepositoryImpl : GoodsRepository {
    private val goodsDao: GoodsDao = GoodsDaoImpl()

    override suspend fun getGoodsList(page: Int) =
        goodsDao.getAllGoods(page * PAGE_SIZE, PAGE_SIZE)

    override suspend fun getSingleGoods(id: Int) = goodsDao.getSingleGoods(id)

    companion object {
        private const val PAGE_SIZE = 30
    }
}

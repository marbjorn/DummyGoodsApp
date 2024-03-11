package com.marbjorn.dummygoodsapp.data

import android.net.Uri
import com.marbjorn.dummygoodsapp.utils.JsonConverter
import com.marbjorn.dummygoodsapp.utils.ResponseUtils
import org.json.JSONObject

class GoodsDaoImpl : GoodsDao {
    private val converter = JsonConverter()

    override suspend fun getAllGoods(startPos: Int, size: Int): List<GoodsModel> {
        val builder = Uri.Builder()
        builder.scheme(SCHEME)
            .authority(URL)
            .appendPath(PRODUCTS_SEGMENT)
            .appendQueryParameter(SKIP_PARAM, startPos.toString())
            .appendQueryParameter(LIMIT_PARAM, size.toString())
        val rawData = ResponseUtils.rawData(builder.build().toString())
        val wrapper = JSONObject(rawData)
        return converter.convertJsonToGoodsListWrapper(wrapper).products.map { it.toModel() }
    }

    override suspend fun getSingleGoods(id: Int): GoodsModel {
        val builder = Uri.Builder()
        builder.scheme(SCHEME)
            .authority(URL)
            .appendPath(PRODUCTS_SEGMENT)
            .appendPath(id.toString())
        val rawData = ResponseUtils.rawData(builder.build().toString())
        return converter.convertJsonToGoodsWrapper(JSONObject(rawData)).toModel()
    }

    companion object {
        private const val URL = "dummyjson.com"
        private const val PRODUCTS_SEGMENT = "products"
        private const val SKIP_PARAM = "skip"
        private const val LIMIT_PARAM = "limit"
        private const val SCHEME = "https"
    }
}

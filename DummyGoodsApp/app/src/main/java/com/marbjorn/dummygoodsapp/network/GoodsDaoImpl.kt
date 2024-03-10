package com.marbjorn.dummygoodsapp.network

import android.net.Uri
import com.marbjorn.dummygoodsapp.GoodsModel
import com.marbjorn.dummygoodsapp.utils.ResponseUtils
import org.json.JSONObject

class GoodsDaoImpl : GoodsDao {
    private val converter = JsonConverter()

    override suspend fun getAllGoods(startPos: Int, size: Int): List<GoodsModel> {
        val builder = Uri.Builder()
        builder.scheme("https")
            .authority(DUMMYJSON_URL)
            .appendPath("products")
            .appendQueryParameter("skip", startPos.toString())
            .appendQueryParameter("limit", size.toString())
        val rawData = ResponseUtils.rawData(builder.build().toString())
        val wrapper = JSONObject(rawData)
        return converter.convertJsonToGoodsListWrapper(wrapper).products.map { it.toModel() }
    }

    override suspend fun getSingleGoods(id: Int): GoodsModel {
        val builder = Uri.Builder()
        builder.scheme("https")
            .authority(DUMMYJSON_URL)
            .appendPath("products")
            .appendPath(id.toString())
        val rawData = ResponseUtils.rawData(builder.build().toString())
        return converter.convertJsonToGoodsWrapper(JSONObject(rawData)).toModel()
    }

    companion object {
        private const val DUMMYJSON_URL = "dummyjson.com"
    }
}
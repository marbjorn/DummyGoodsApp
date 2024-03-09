package com.marbjorn.dummygoodsapp

import android.net.Uri
import android.util.Log
import org.json.JSONObject

class GoodsDaoImpl : GoodsDao {

    override suspend fun getGoods(skip: Int, limit: Int): List<GoodsModel> {
        val builder = Uri.Builder()
        builder.scheme("https")
            .authority(DUMMYJSON_URL)
            .appendPath("products")
            .appendQueryParameter("skip", skip.toString())
            .appendQueryParameter("limit", limit.toString())
        val rawData = ResponseHelper.rawData(builder.build().toString())
        val products = JSONObject(rawData).getJSONArray("products")
        val resultList = mutableListOf<GoodsModel>()
        for (i in 0 until products.length()) {
            resultList += convertJsonToGoods(products[i] as JSONObject)
        }
        Log.d(TAG, resultList.toString())
        return resultList
    }

    private fun convertJsonToGoods(obj : JSONObject) : GoodsModel {
        val imagesList = mutableListOf<String>()
        val images = obj.getJSONArray("images")
        for (i in 0 until images.length()) {
            imagesList += images[i].toString()
        }
        val model = GoodsModel(
            id = obj.getInt("id"),
            title = obj.getString("title"),
            description = obj.getString("description"),
            price = obj.getDouble("price"),
            images = imagesList,
            thumbnailUrl = obj.getString("thumbnail")
        )

        return model
    }

    companion object {
        const val TAG = "GoodsDaoImpl"
        private const val DUMMYJSON_URL = "dummyjson.com"
        private const val SKIP_PARAM = "skip="
        private const val LIMIT_PARAM = "limit="
    }
}
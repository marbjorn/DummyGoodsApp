package com.marbjorn.dummygoodsapp.utils

import com.marbjorn.dummygoodsapp.data.GoodsListWrapper
import org.json.JSONObject

class JsonConverter {
    fun convertJsonToGoodsListWrapper(obj: JSONObject): GoodsListWrapper {
        val products = obj.getJSONArray("products")
        val goodsWrapperList = emptyList<GoodsListWrapper.GoodsWrapper>().toMutableList()
        for (i in 0 until products.length()) {
            goodsWrapperList += convertJsonToGoodsWrapper(products.get(i) as JSONObject)
        }

        return GoodsListWrapper(
            products = goodsWrapperList,
            limit = obj.getInt("limit"),
            skip = obj.getInt("skip"),
            total = obj.getInt("total")
        )
    }

    fun convertJsonToGoodsWrapper(obj: JSONObject): GoodsListWrapper.GoodsWrapper {
        val imagesList = mutableListOf<String>()
        val images = obj.getJSONArray("images")
        for (i in 0 until images.length()) {
            imagesList += images[i].toString()
        }
        return GoodsListWrapper.GoodsWrapper(
            id = obj.getInt("id"),
            title = obj.getString("title"),
            description = obj.getString("description"),
            price = obj.getDouble("price"),
            discountPercentage = obj.getDouble("discountPercentage"),
            rating = obj.getDouble("rating"),
            stock = obj.getInt("stock"),
            brand = obj.getString("brand"),
            category = obj.getString("category"),
            images = imagesList,
            thumbnailUrl = obj.getString("thumbnail")
        )
    }
}

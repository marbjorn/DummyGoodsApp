package com.marbjorn.dummygoodsapp.network

import com.marbjorn.dummygoodsapp.GoodsModel

data class GoodsListWrapper(
    val products : List<GoodsWrapper>,
    val total : Int,
    val skip : Int,
    val limit : Int,
) {
    data class GoodsWrapper(
        val id : Int,
        val title : String,
        val description : String,
        val price : Double,
        val discountPercentage: Double,
        val rating : Double,
        val stock : Int,
        val brand : String,
        val category: String,
        val thumbnailUrl : String,
        val images : List<String>
    ) {
        fun toModel() : GoodsModel {
            return with (this) {
                GoodsModel(
                    id = id,
                    title = title,
                    description = description,
                    images = images,
                    thumbnailUrl = thumbnailUrl,
                    price = price,
                    discountPercentage = discountPercentage,
                    stock = stock,
                    rating = rating
                )
            }
        }
    }
}
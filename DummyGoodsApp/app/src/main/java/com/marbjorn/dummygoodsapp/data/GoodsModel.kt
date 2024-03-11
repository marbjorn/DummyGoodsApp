package com.marbjorn.dummygoodsapp.data

data class GoodsModel(
    var id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val thumbnailUrl: String,
    val images: List<String>,
    val rating: Double? = null,
    val discountPercentage: Double? = null,
    val stock: Int? = null
)

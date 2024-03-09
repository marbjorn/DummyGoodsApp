package com.marbjorn.dummygoodsapp

data class GoodsModel(
    val id : Int,
    val title : String,
    val description : String,
    val price : Double,
    val thumbnailUrl : String,
    val images : List<String>
)
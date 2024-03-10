package com.marbjorn.dummygoodsapp

data class GoodsModel(
    var id : Int,
    val title : String,
    val description : String,
    val price : Double,
    val thumbnailUrl : String,
    val images : List<String>
)
package com.marbjorn.dummygoodsapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.marbjorn.dummygoodsapp.R

fun ImageView.loadThumbnail(url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.placeholder_background)
        .into(this)
}

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
}

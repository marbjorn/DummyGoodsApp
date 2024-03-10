package com.marbjorn.dummygoodsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marbjorn.dummygoodsapp.GoodsModel
import com.marbjorn.dummygoodsapp.R
import com.marbjorn.dummygoodsapp.databinding.GoodsItemBinding
import com.marbjorn.dummygoodsapp.ui.GoodsInfoFragment
import com.marbjorn.dummygoodsapp.ui.GoodsListFragmentDirections

class GoodsAdapter(val navController: NavController) : PagingDataAdapter<GoodsModel, GoodsAdapter.GoodsViewHolder>(differCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val binding = GoodsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        getItem(position)!!.let { holder.bind(it) }
        holder.setIsRecyclable(false)
    }

    inner class GoodsViewHolder(val goodsItemBinding: GoodsItemBinding) : RecyclerView.ViewHolder(goodsItemBinding.root) {
        fun bind(item: GoodsModel) = with (goodsItemBinding) {
            title.text = item.title
            description.text = item.description
            loadImage(pic, item.thumbnailUrl)
            root.setOnClickListener {
                val action = GoodsListFragmentDirections
                    .actionGoodsListFragment2ToGoodsInfoFragment2(item.id)
                navController.navigate(action)
            }
        }
    }


    companion object {
        @JvmStatic
        fun loadImage(thumbs: ImageView, url: String) {
            Glide.with(thumbs)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_autorenew_24)
                .into(thumbs)
        }

        val differCallback = object : DiffUtil.ItemCallback<GoodsModel>() {
            override fun areItemsTheSame(oldItem: GoodsModel, newItem: GoodsModel): Boolean =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: GoodsModel, newItem: GoodsModel): Boolean =
                oldItem == newItem
        }
    }
}
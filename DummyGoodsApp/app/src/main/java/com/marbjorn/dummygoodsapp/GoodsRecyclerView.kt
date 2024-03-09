package com.marbjorn.dummygoodsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marbjorn.dummygoodsapp.databinding.FragmentGoodsListBinding
import com.marbjorn.dummygoodsapp.databinding.GoodsItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoodsAdapter(lifecycleOwner: LifecycleOwner, viewModel: GoodsListViewModel)
    : RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>() {

    private var goods_ : MutableList<GoodsModel> = mutableListOf()

    val goods : List<GoodsModel>
        get() = goods_

    init {
        viewModel.goodsList.observe(lifecycleOwner) {
            setList(it)
        }
    }

    fun setList(newList : List<GoodsModel>) {
        val callback = DiffCallback(goods, newList)
        val diff = DiffUtil.calculateDiff(callback)
        goods_.addAll(newList)
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val binding = GoodsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return GoodsViewHolder(binding)
    }

    override fun getItemCount(): Int = goods.size

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) =
        holder.bind(goods[position])

    inner class GoodsViewHolder(val goodsItemBinding: GoodsItemBinding) : RecyclerView.ViewHolder(goodsItemBinding.root) {
        fun bind(item: GoodsModel) = with (goodsItemBinding) {
            title.text = item.title
            description.text = item.description
            loadImage(pic, item.thumbnailUrl)
        }
    }


    companion object {
        @JvmStatic
        fun loadImage(thumbs: ImageView, url: String) {
            Glide.with(thumbs)
                .load(url)
                .centerCrop()
                .into(thumbs)
        }
    }
}

class DiffCallback(val oldList : List<GoodsModel>, val newList: List<GoodsModel>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.title == newItem.title
                && oldItem.description == newItem.description
    }

}
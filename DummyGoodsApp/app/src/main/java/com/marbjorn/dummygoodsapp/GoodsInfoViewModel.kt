package com.marbjorn.dummygoodsapp

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.ViewSwitcher
import androidx.core.view.children
import androidx.core.view.size
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerSize
import com.google.android.material.shape.ShapeAppearanceModel
import com.marbjorn.dummygoodsapp.databinding.FragmentGoodsInfoBinding
import com.marbjorn.dummygoodsapp.databinding.GoodsItemBinding
import com.marbjorn.dummygoodsapp.network.GoodsRepository
import com.marbjorn.dummygoodsapp.paging.GoodsPagingSource
import com.marbjorn.dummygoodsapp.ui.GoodsInfoFragmentArgs
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

class GoodsInfoViewModel : ViewModel() {

    var goodsModel : MutableLiveData<GoodsModel> = MutableLiveData(null)

    fun updateModel(binding : FragmentGoodsInfoBinding, id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val modelDeffered = async(SupervisorJob()) {
                    repo.getSingleGoods(id)
                }
                goodsModel.postValue(modelDeffered.await())
            } catch (_: Exception) {
                binding.llError.visibility = View.VISIBLE
                this.cancel()
            }
        }
    }

    fun setUi(binding : FragmentGoodsInfoBinding, context : Context) : Unit = with(binding) {
        val model = goodsModel.value ?: return
        title.text = model.title
        description.text = model.description
        price.text = model.price.toString()
        discount.text = "-${model.discountPercentage}%"
        rating.text = model.rating.toString()
        stock.text = "Stock: ${model.stock.toString()}"
        setImageSwitcher(binding, context, model)
        llError.visibility = View.GONE
    }

    private fun setImageSwitcher(binding: FragmentGoodsInfoBinding, context : Context, model : GoodsModel) {
        model.images.forEach {
            val imageView = ShapeableImageView(context)
            imageView.adjustViewBounds = true
            imageView.layoutParams = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            imageView.shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(16f)
            loadImage(imageView, it)
            binding.imageSwitcher.addView(imageView)
        }
    }


    companion object {
        @JvmStatic
        fun loadImage(image: ImageView, url: String) {
            Glide.with(image)
                .load(url)
                .centerCrop()
                .into(image)
        }
        private val repo = GoodsRepository()
    }
}
package com.marbjorn.dummygoodsapp.vm

import android.app.ActionBar.LayoutParams
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.marbjorn.dummygoodsapp.data.GoodsModel
import com.marbjorn.dummygoodsapp.data.GoodsRepository
import com.marbjorn.dummygoodsapp.databinding.FragmentGoodsInfoBinding
import com.marbjorn.dummygoodsapp.utils.loadImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class GoodsInfoViewModel(private val repository: GoodsRepository) : ViewModel() {

    private var _goodsModel: MutableLiveData<GoodsModel> = MutableLiveData()

    var goodsModel: LiveData<GoodsModel> = _goodsModel

    fun updateModel(binding: FragmentGoodsInfoBinding, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val modelDeffered = async(SupervisorJob()) {
                    repository.getSingleGoods(id)
                }
                _goodsModel.postValue(modelDeffered.await())
            } catch (_: Exception) {
                binding.llError.visibility = View.VISIBLE
                this.cancel()
            }
        }
    }

    fun setUi(binding: FragmentGoodsInfoBinding): Unit = with(binding) {
        val model = goodsModel.value ?: return
        title.text = model.title
        description.text = model.description
        price.text = model.price.toString()
        discount.text = "-${model.discountPercentage}%"
        rating.text = model.rating.toString()
        stock.text = "Stock: ${model.stock}"
        updateImageContainer(binding, model)
        llError.visibility = View.GONE
    }

    private fun updateImageContainer(binding: FragmentGoodsInfoBinding, model: GoodsModel) {
        binding.imageSwitcher.removeAllViews()
        model.images.forEach {
            val imageView = ShapeableImageView(binding.root.context)
            imageView.adjustViewBounds = true
            val params = ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            imageView.layoutParams = params
            imageView.shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(CORNER_SIZE)
            imageView.loadImage(it)
            binding.imageSwitcher.addView(imageView)
        }
    }
    companion object {
        private const val CORNER_SIZE = 16f
    }
}

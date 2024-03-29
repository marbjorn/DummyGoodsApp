package com.marbjorn.dummygoodsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.marbjorn.dummygoodsapp.databinding.FragmentGoodsInfoBinding
import com.marbjorn.dummygoodsapp.vm.GoodsInfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GoodsInfoFragment : Fragment() {

    private val args: GoodsInfoFragmentArgs by navArgs()

    private var _binding: FragmentGoodsInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GoodsInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoodsInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.goodsModel.observe(viewLifecycleOwner) {
            viewModel.setUi(binding)
        }
        binding.btnRetry.setOnClickListener {
            viewModel.updateModel(binding, args.modelId)
        }
        viewModel.updateModel(binding, args.modelId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

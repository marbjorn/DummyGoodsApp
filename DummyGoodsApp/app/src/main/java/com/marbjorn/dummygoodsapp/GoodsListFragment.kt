package com.marbjorn.dummygoodsapp

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marbjorn.dummygoodsapp.databinding.FragmentGoodsListBinding

class GoodsListFragment : Fragment() {

    private var _binding: FragmentGoodsListBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = GoodsListFragment()
    }

    private val viewModel: GoodsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoodsListBinding.inflate(inflater, container, false)
        val view = binding.root
        val layoutManager = LinearLayoutManager(this.context)
        binding.rvList.layoutManager = layoutManager
        val rvAdapter = GoodsAdapter(this.viewLifecycleOwner, viewModel)
        binding.rvList.adapter = rvAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateGoodsList(0, 30)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
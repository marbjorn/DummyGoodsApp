package com.marbjorn.dummygoodsapp.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.marbjorn.dummygoodsapp.GoodsListViewModel
import com.marbjorn.dummygoodsapp.adapter.GoodsAdapter
import com.marbjorn.dummygoodsapp.adapter.PaginationAdapter
import com.marbjorn.dummygoodsapp.databinding.FragmentGoodsListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GoodsListFragment : Fragment() {

    private var _binding: FragmentGoodsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter : GoodsAdapter

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
        rvAdapter = GoodsAdapter()
        binding.rvList.adapter = rvAdapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.goodsList.collectLatest {
                rvAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            rvAdapter.loadStateFlow.collect{
                val state = it.refresh
                binding.prgBarMovies.isVisible = state is LoadState.Loading
                binding.llError.isVisible = state is LoadState.Error
            }
        }

        binding.btnRetry.setOnClickListener {
            rvAdapter.retry()
        }


        binding.rvList.adapter = rvAdapter.withLoadStateFooter(
           PaginationAdapter{
                rvAdapter.retry()
            }
        )

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
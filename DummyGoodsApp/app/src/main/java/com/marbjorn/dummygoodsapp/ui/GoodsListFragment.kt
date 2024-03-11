package com.marbjorn.dummygoodsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.marbjorn.dummygoodsapp.adapter.GoodsAdapter
import com.marbjorn.dummygoodsapp.adapter.PaginationAdapter
import com.marbjorn.dummygoodsapp.databinding.FragmentGoodsListBinding
import com.marbjorn.dummygoodsapp.vm.GoodsListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GoodsListFragment : Fragment() {

    private var _binding: FragmentGoodsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: GoodsAdapter

    private val viewModel: GoodsListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoodsListBinding.inflate(inflater, container, false)
        val view = binding.root
        val layoutManager = LinearLayoutManager(this.context)
        binding.rvList.layoutManager = layoutManager
        rvAdapter = GoodsAdapter(findNavController())
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
            rvAdapter.loadStateFlow.collect {
                if (_binding != null) {
                    val state = it.refresh
                    binding.prgBarMovies.isVisible = state is LoadState.Loading
                    binding.llError.visibility = if (state is LoadState.Error) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }

        binding.btnRetry.setOnClickListener {
            rvAdapter.retry()
        }

        binding.rvList.adapter = rvAdapter.withLoadStateFooter(
            PaginationAdapter {
                rvAdapter.retry()
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

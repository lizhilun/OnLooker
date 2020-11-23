package com.lizl.onlooker.mvvm.fragment

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.lizl.news.mvvm.base.BaseFragment
import com.lizl.onlooker.R
import com.lizl.onlooker.custom.other.ListDividerItemDecoration
import com.lizl.onlooker.databinding.FragmentWeiboListBinding
import com.lizl.onlooker.mvvm.adapter.WBAdapter
import com.lizl.onlooker.mvvm.viewmodel.WbHomeViewModel
import com.lizl.onlooker.util.SkinUtil
import com.lizl.onlooker.util.WBUtil

class WbListFragment : BaseFragment<FragmentWeiboListBinding>(R.layout.fragment_weibo_list)
{
    private val wbHomeViewModel: WbHomeViewModel by lazy {
        AndroidViewModelFactory.getInstance(requireActivity().application).create(WbHomeViewModel::class.java)
    }

    private val wbAdapter: WBAdapter by lazy { WBAdapter() }

    override fun initView()
    {
        dataBinding.wbAdapter = wbAdapter

        dataBinding.srlLayout.let {
            it.setEnableRefresh(true)
            it.setEnableLoadMore(false)

            it.setOnRefreshListener { wbHomeViewModel.refreshMoreData() }
        }

        dataBinding.rvWbList.addItemDecoration(ListDividerItemDecoration(resources.getDimensionPixelSize(R.dimen.weibo_divider_line_height),
                SkinUtil.getColor(requireContext(), R.color.colorDivideLine)))

        wbAdapter.loadMoreModule?.let {
            it.isEnableLoadMore = true
            it.preLoadNumber = 5

            it.setOnLoadMoreListener { wbHomeViewModel.loadMoreData() }
        }
    }

    override fun initData()
    {
        wbHomeViewModel.wbListLiveData.observe(this, {
            dataBinding.srlLayout.finishRefresh()
            wbAdapter.loadMoreModule?.loadMoreComplete()
            wbAdapter.setDiffNewData(it)
            WBUtil.saveWBCache(it)
        })

        wbHomeViewModel.requestData()
    }
}
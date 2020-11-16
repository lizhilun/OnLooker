package com.lizl.onlooker.mvvm.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.lizl.news.mvvm.base.BaseFragment
import com.lizl.onlooker.R
import com.lizl.onlooker.databinding.FragmentWeiboListBinding
import com.lizl.onlooker.mvvm.adapter.WBAdapter
import com.lizl.onlooker.mvvm.viewmodel.WbHomeViewModel

class WbListFragment : BaseFragment<FragmentWeiboListBinding>(R.layout.fragment_weibo_list)
{
    private val wbHomeViewModel: WbHomeViewModel by lazy {
        AndroidViewModelFactory.getInstance(requireActivity().application).create(WbHomeViewModel::class.java)
    }

    private val wbAdapter: WBAdapter by lazy { WBAdapter() }

    override fun initView()
    {
        dataBinding.wbAdapter = wbAdapter
    }

    override fun initData()
    {
        wbHomeViewModel.wbListLiveData.observe(this, Observer {
            wbAdapter.setDiffNewData(it)
        })

        wbHomeViewModel.requestData()
    }
}
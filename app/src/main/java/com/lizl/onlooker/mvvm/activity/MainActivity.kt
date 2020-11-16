package com.lizl.onlooker.mvvm.activity

import com.lizl.onlooker.R
import com.lizl.onlooker.databinding.ActivityMainBinding
import com.lizl.onlooker.mvvm.adapter.FragmentPagerAdapter
import com.lizl.onlooker.mvvm.base.BaseActivity
import com.lizl.onlooker.mvvm.fragment.WbListFragment

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main)
{
    override fun initView()
    {
        val fragmentPagerAdapter = FragmentPagerAdapter(this)
        dataBinding.vpMain.adapter = fragmentPagerAdapter
        dataBinding.vpMain.offscreenPageLimit = 3

        fragmentPagerAdapter.setFragmentList(mutableListOf(WbListFragment()))
    }
}
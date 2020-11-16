package com.lizl.onlooker.mvvm.activity

import com.blankj.utilcode.util.ActivityUtils
import com.lizl.onlooker.R
import com.lizl.onlooker.databinding.ActivitySplashBinding
import com.lizl.onlooker.mvvm.base.BaseActivity
import com.lizl.onlooker.util.AccessTokenUtil

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash)
{
    override fun initView()
    {
        if (AccessTokenUtil.isTokenSessionValid())
        {
            ActivityUtils.startActivity(MainActivity::class.java)
        }
        else
        {
            ActivityUtils.startActivity(LoginActivity::class.java)
        }
    }
}
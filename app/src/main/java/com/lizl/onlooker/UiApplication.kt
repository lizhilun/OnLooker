package com.lizl.onlooker

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.lizl.onlooker.constant.AppConstant
import com.lizl.onlooker.util.SkinUtil
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo

class UiApplication : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        Utils.init(this)

        SkinUtil.init(this)

        WbSdk.install(this, AuthInfo(this, AppConstant.APP_KEY, AppConstant.REDIRECT_URL, AppConstant.SCOPE))
    }
}
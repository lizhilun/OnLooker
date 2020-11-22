package com.lizl.onlooker

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.lizl.onlooker.constant.AppConstant
import com.lizl.onlooker.util.SkinUtil
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.BallPulseFooter
import com.sina.weibo.sdk.WbSdk
import com.sina.weibo.sdk.auth.AuthInfo

class UiApplication : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        Utils.init(this)

        SkinUtil.init(this)

        //初始化SmartRefreshLayout全局Header
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary, R.color.white) //全局设置主题颜色
            MaterialHeader(context)
        }
        //初始化SmartRefreshLayout全局Footer
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            BallPulseFooter(context)
        }

        WbSdk.install(this, AuthInfo(this, AppConstant.APP_KEY, AppConstant.REDIRECT_URL, AppConstant.SCOPE))
    }
}
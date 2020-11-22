package com.lizl.onlooker.util

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.blankj.utilcode.util.Utils
import skin.support.SkinCompatManager
import skin.support.SkinCompatManager.SkinLoaderListener
import skin.support.app.SkinAppCompatViewInflater
import skin.support.constraint.app.SkinConstraintViewInflater
import skin.support.content.res.SkinCompatResources
import skin.support.design.app.SkinMaterialViewInflater

object SkinUtil
{
    private const val SKIN_DARK = "dark"

    fun init(application: Application)
    {
        SkinCompatManager.withoutActivity(application).addInflater(SkinAppCompatViewInflater())           // 基础控件换肤初始化
            .addInflater(SkinMaterialViewInflater())            // material design 控件换肤初始化[可选]
            .addInflater(SkinConstraintViewInflater())          // ConstraintLayout 控件换肤初始化[可选]
            .setSkinWindowBackgroundEnable(true)               // windowBg换肤
            .setSkinStatusBarColorEnable(true)
        loadSkin()
    }

    fun loadSkin(callback: ((Boolean) -> Unit)? = null)
    {
        val skinLoadListener = object : SkinLoaderListener
        {
            override fun onSuccess()
            {
                callback?.invoke(true)
            }

            override fun onFailed(errMsg: String?)
            {
                callback?.invoke(false)
            }

            override fun onStart()
            {
            }
        }

        if (isNightModeOn())
        {
            SkinCompatManager.getInstance().loadSkin(SKIN_DARK, skinLoadListener, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN)
        }
        else
        {
            SkinCompatManager.getInstance().loadSkin("", skinLoadListener, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN)
        }
    }

    fun getColor(context: Context, colorResId: Int) = SkinCompatResources.getColor(context, colorResId)

    fun isNightModeOn(): Boolean
    {
        return isSystemDarkMode()
    }

    private fun isSystemDarkMode(): Boolean
    {
        return Utils.getApp().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

}
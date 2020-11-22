package com.lizl.onlooker.custom.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import com.blankj.utilcode.util.ScreenUtils
import com.lizl.onlooker.R

open class BaseDialog(context: Context, private val layoutResId: Int) : Dialog(context, R.style.GlobalDialogStyle)
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        initView()
        initData()
    }

    override fun onStart()
    {
        super.onStart()

        val params = window?.attributes
        params?.width = getDialogWidth()
        params?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window?.attributes = params
    }

    open fun initView()
    {

    }

    open fun initData()
    {

    }

    open fun getDialogWidth(): Int
    {
        return (ScreenUtils.getScreenHeight().coerceAtMost(ScreenUtils.getScreenWidth()) * 0.8).toInt()
    }
}
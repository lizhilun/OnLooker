package com.lizl.onlooker.custom.popup

import android.content.Context
import com.lizl.onlooker.R
import com.lxj.xpopup.core.CenterPopupView
import kotlinx.android.synthetic.main.popup_info.view.*

class PopupInfo(context: Context, private val content: String) : CenterPopupView(context)
{
    override fun getImplLayoutId() = R.layout.popup_info

    override fun onCreate()
    {
        popupInfo?.let {
            it.hasShadowBg = true
        }

        tv_content.text = content
    }
}
package com.lizl.onlooker.custom.popup

import android.content.Context
import com.lizl.onlooker.R
import com.lxj.xpopup.core.CenterPopupView
import kotlinx.android.synthetic.main.popup_input.view.*

class PopupInput(context: Context, private val title: String, private val onInputFinish: (String) -> Unit) : CenterPopupView(context)
{
    override fun getImplLayoutId() = R.layout.popup_input

    override fun onCreate()
    {
        tv_title.text = title

        popupInfo?.let {
            it.autoOpenSoftInput = true
        }

        tv_cancel.setOnClickListener { dismiss() }

        tv_confirm.setOnClickListener {
            if (et_input.text.toString().isBlank())
            {
                return@setOnClickListener
            }
            onInputFinish.invoke(et_input.text.toString())
            dismiss()
        }
    }
}
package com.lizl.onlooker.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lizl.onlooker.custom.view.CustomWBLayout
import com.lizl.onlooker.mvvm.model.weibo.WbModel

object DataBindingUtil
{
    @JvmStatic
    @BindingAdapter("app:adapter")
    fun bindAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>?)
    {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("app:wbModel")
    fun bindWbModel(customWBLayout: CustomWBLayout, wbModel: WbModel?)
    {
        wbModel ?: return
        customWBLayout.bindWBModel(wbModel)
    }

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun bindImageUrl(imageView: ImageView, imageUrl: Any?)
    {
        imageUrl ?: return
        ImageUtil.displayImage(imageView, imageUrl)
    }
}
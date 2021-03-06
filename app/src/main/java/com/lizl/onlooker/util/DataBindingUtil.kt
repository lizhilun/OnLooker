package com.lizl.onlooker.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.lizl.onlooker.custom.view.CustomWBImageView
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

    @JvmStatic
    @BindingAdapter("app:oriImageUrl")
    fun bindOriImageUrl(imageView: ImageView, imageUrl: Any?)
    {
        imageUrl ?: return
        ImageUtil.displayOriImage(imageView, imageUrl)
    }

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun bindImageUrl(imageView: CustomWBImageView, imageUrl: String?)
    {
        imageUrl ?: return
        imageView.bindImage(imageUrl)
    }

    @JvmStatic
    @BindingAdapter("app:overScrollMode")
    fun bindOverScrollModel(viewPager: ViewPager2, mode: Int)
    {
        val child = viewPager.getChildAt(0)
        if (child is RecyclerView)
        {
            child.overScrollMode = mode
        }
    }
}
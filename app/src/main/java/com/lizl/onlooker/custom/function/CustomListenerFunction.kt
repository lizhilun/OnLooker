package com.lizl.onlooker.custom.function

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter

fun RecyclerView.addOnScrollStateChangedListener(callback: (newState: Int) -> Unit)
{
    addOnScrollListener(object : RecyclerView.OnScrollListener()
    {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int)
        {
            super.onScrollStateChanged(recyclerView, newState)
            callback.invoke(newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int)
        {
            super.onScrolled(recyclerView, dx, dy)
        }
    })
}

fun ViewPager2.registerOnPageChangeCallback(onPageSelectedListener: (position: Int) -> Unit)
{
    this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback()
    {
        override fun onPageSelected(position: Int)
        {
            onPageSelectedListener.invoke(position)
        }
    })
}

fun <T> BaseQuickAdapter<T, *>.setOnItemClickListener(listener: (model: T) -> Unit)
{
    setOnItemClickListener { _, _, position ->
        val model = data.getOrNull(position)
        if (model != null)
        {
            listener.invoke(model)
        }
    }
}

fun <T> BaseQuickAdapter<T, *>.setOnItemLongClickListener(listener: (model: T) -> Unit)
{
    setOnItemLongClickListener { _, _, position ->
        val model = data.getOrNull(position)
        if (model != null)
        {
            listener.invoke(model)
        }
        true
    }
}
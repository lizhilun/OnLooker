package com.lizl.onlooker.custom.function

import androidx.recyclerview.widget.RecyclerView

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
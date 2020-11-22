package com.lizl.onlooker.custom.other

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListDividerItemDecoration(private val dividerSize: Int, private val dividerColor: Int) : RecyclerView.ItemDecoration()
{
    private val mPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            color = dividerColor
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
    {
        outRect.top = if (parent.getChildLayoutPosition(view) == 0) 0 else dividerSize
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
    {
        super.onDraw(c, parent, state)

        val childCount = parent.childCount
        for (i in 0 until childCount)
        {
            val view = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(view)
            if (index == 0)
            {
                continue
            }
            val dividerTop: Float = view.top - dividerSize.toFloat()
            val dividerLeft: Float = parent.paddingLeft.toFloat()
            val dividerBottom = view.top.toFloat()
            val dividerRight: Float = parent.width - parent.paddingRight.toFloat()
            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint)
        }
    }
}
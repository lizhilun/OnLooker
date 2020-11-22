package com.lizl.onlooker.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.lizl.onlooker.R
import com.lizl.onlooker.util.ImageUtil
import kotlinx.android.synthetic.main.layout_wb_image.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CustomWBImageView(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle)
{
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init
    {
        initView()
    }

    private fun initView()
    {
        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_wb_image, null)
        addView(contentView)
    }

    fun bindImage(imageUrl: String, maxSize: Int = 0, minSize: Int = 0)
    {
        iv_image_type_gif.isVisible = ImageUtil.isGifImage(imageUrl)
        iv_image_type_long.isVisible = false

        ImageUtil.loadImage(context, imageUrl) {
            iv_image_type_long.isVisible = ImageUtil.isLargeImage(it.width, it.height)
            if (maxSize > 0 && minSize > 0)
            {
                GlobalScope.launch {
                    val adaptiveBitmap = ImageUtil.bitmapAdaptive(it, maxSize, maxSize, minSize, minSize)
                    iv_image?.post { iv_image.setImageBitmap(adaptiveBitmap) }
                }
            }
            else
            {
                iv_image?.post { iv_image.setImageBitmap(it) }
            }
        }
    }
}
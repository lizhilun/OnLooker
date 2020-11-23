package com.lizl.onlooker.custom.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.lizl.onlooker.R
import com.lizl.onlooker.custom.skin.SkinImageView
import com.lizl.onlooker.util.SkinUtil
import skin.support.widget.SkinCompatTextView

class ImageTextView(context: Context, attrs: AttributeSet?, defStyle: Int) : ConstraintLayout(context, attrs, defStyle)
{
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init
    {
        initView(context, attrs)
    }

    companion object
    {
        private const val DRAWABLE_GRAVITY_START = 1
        private const val DRAWABLE_GRAVITY_TOP = 2
        private const val DRAWABLE_GRAVITY_END = 3
        private const val DRAWABLE_GRAVITY_BOTTOM = 4
    }

    private lateinit var imageView: SkinImageView
    private lateinit var textView: SkinCompatTextView

    private fun initView(context: Context, attrs: AttributeSet?)
    {
        imageView = SkinImageView(context).apply {
            id = generateViewId()
            addView(this)
        }

        textView = SkinCompatTextView(context).apply {
            id = generateViewId()
            addView(this)
        }

        var drawableWidth = 10
        var drawableHeight = 10
        var drawablePadding = 10
        var drawableGravity = DRAWABLE_GRAVITY_START
        var drawableResId: Int? = null
        var drawableTintResId: Int? = null

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextView)
        val n = typedArray.indexCount
        for (i in 0 until n)
        {
            when (val attr = typedArray.getIndex(i))
            {
                R.styleable.ImageTextView_textSize -> textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, typedArray.getDimension(attr, 10F))
                R.styleable.ImageTextView_textColor -> textView.setTextColor(typedArray.getColor(attr, SkinUtil.getColor(context, R.color.colorTextColor)))
                R.styleable.ImageTextView_drawable -> drawableResId = typedArray.getResourceId(attr, R.color.transparent)
                R.styleable.ImageTextView_drawableTint -> drawableTintResId = typedArray.getResourceId(attr, R.color.colorSvgTint)
                R.styleable.ImageTextView_drawableWidth -> drawableWidth = typedArray.getLayoutDimension(attr, drawableWidth)
                R.styleable.ImageTextView_drawableHeight -> drawableHeight = typedArray.getLayoutDimension(attr, drawableHeight)
                R.styleable.ImageTextView_drawablePadding -> drawablePadding = typedArray.getLayoutDimension(attr, drawablePadding)
                R.styleable.ImageTextView_drawableGravity -> drawableGravity = typedArray.getLayoutDimension(attr, drawableGravity)
            }
        }
        typedArray.recycle()

        drawableTintResId?.let { imageView.setTintResId(it) }
        drawableResId?.let { imageView.setImageResource(it) }

        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        when (drawableGravity)
        {
            DRAWABLE_GRAVITY_START ->
            {
                constraintSet.constrainWidth(imageView.id, drawableWidth)
                constraintSet.constrainHeight(imageView.id, drawableHeight)
                constraintSet.connect(imageView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                constraintSet.connect(imageView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                constraintSet.connect(imageView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
                constraintSet.connect(imageView.id, ConstraintSet.END, textView.id, ConstraintSet.START, drawablePadding)
                constraintSet.setHorizontalChainStyle(imageView.id, ConstraintSet.CHAIN_PACKED)

                constraintSet.constrainWidth(imageView.id, LayoutParams.WRAP_CONTENT)
                constraintSet.constrainHeight(imageView.id, LayoutParams.WRAP_CONTENT)
                constraintSet.connect(textView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
                constraintSet.connect(textView.id, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
                constraintSet.connect(textView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
                constraintSet.connect(textView.id, ConstraintSet.START, imageView.id, ConstraintSet.END)
            }
        }

        constraintSet.applyTo(this)
    }

    fun setText(text: String)
    {
        textView.text = text
    }
}
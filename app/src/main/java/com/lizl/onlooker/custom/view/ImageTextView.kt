package com.lizl.onlooker.custom.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.lizl.onlooker.R

class ImageTextView(context: Context, attrs: AttributeSet?, defStyle: Int) : AppCompatTextView(context, attrs, defStyle)
{
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, Resources.getSystem().getIdentifier("textViewStyle", "attr", "android"))

    private var leftDrawableWidth = 10
    private var leftDrawableHeight = 10
    private var topDrawableWidth = 10
    private var topDrawableHeight = 10
    private var rightDrawableWidth = 10
    private var rightDrawableHeight = 10
    private var bottomDrawableWidth = 10
    private var bottomDrawableHeight = 10

    private var left: Drawable? = null
    private var top: Drawable? = null
    private var right: Drawable? = null
    private var bottom: Drawable? = null

    init
    {
        getAttributes(context, attrs, defStyle)
    }

    private fun getAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
    {
        /**
         * 获得我们所定义的自定义样式属性
         */
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ImageTextView, defStyleAttr, 0)
        val n = typedArray.indexCount
        for (i in 0 until n)
        {
            when (val attr = typedArray.getIndex(i))
            {
                R.styleable.ImageTextView_drawableWidth_left -> leftDrawableWidth = typedArray.getLayoutDimension(attr, 10)
                R.styleable.ImageTextView_drawableHeight_left -> leftDrawableHeight = typedArray.getLayoutDimension(attr, 10)
                R.styleable.ImageTextView_drawableWidth_top -> topDrawableWidth = typedArray.getLayoutDimension(attr, 10)
                R.styleable.ImageTextView_drawableHeight_top -> topDrawableHeight = typedArray.getLayoutDimension(attr, 10)
                R.styleable.ImageTextView_drawableWidth_right -> rightDrawableWidth = typedArray.getLayoutDimension(attr, 10)
                R.styleable.ImageTextView_drawableHeight_right -> rightDrawableHeight = typedArray.getLayoutDimension(attr, 10)
                R.styleable.ImageTextView_drawableWidth_bottom -> bottomDrawableWidth = typedArray.getLayoutDimension(attr, 10)
                R.styleable.ImageTextView_drawableHeight_bottom -> bottomDrawableHeight = typedArray.getLayoutDimension(attr, 10)
            }
        }
        typedArray.recycle()

        /*
         * setCompoundDrawablesWithIntrinsicBounds方法会首先在父类的构造方法中执行，
         * 彼时执行时drawable的大小还都没有开始获取，都是0,
         * 这里获取完自定义的宽高属性后再次调用这个方法，插入drawable的大小
         * */
        setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(left: Drawable?, top: Drawable?, right: Drawable?, bottom: Drawable?)
    {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom

        left?.setBounds(0, 0, leftDrawableWidth, leftDrawableHeight)
        right?.setBounds(0, 0, rightDrawableWidth, rightDrawableHeight)
        top?.setBounds(0, 0, topDrawableWidth, topDrawableHeight)
        bottom?.setBounds(0, 0, bottomDrawableWidth, bottomDrawableHeight)

        setCompoundDrawables(left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas)
    {
        val mPaint = paint
        val drawables = compoundDrawables
        val drawableTop = drawables[1]
        val drawableLeft = drawables[0]
        if (drawableTop != null)
        {
            var textHeight = 0f
            if (!TextUtils.isEmpty(text.toString()))
            {
                textHeight = mPaint.descent() - mPaint.ascent()
            }
            val drawablePadding = compoundDrawablePadding
            val drawableHeight = drawableTop.bounds.height()
            val bodyHeight = textHeight + drawableHeight.toFloat() + drawablePadding.toFloat()
            canvas.translate(0f, (height - bodyHeight) / 2)
        }
        else if (drawableLeft != null)
        {
            val textWidth = paint.measureText(text.toString())
            val drawablePadding = compoundDrawablePadding
            val drawableWidth = drawableLeft.bounds.width()
            val bodyWidth = textWidth + drawableWidth.toFloat() + drawablePadding.toFloat()
            if (drawableWidth > 10)
            {
                canvas.translate((width - bodyWidth) / 2, 0f)
            }
            else
            {
                canvas.translate((width - textWidth) / 2 - drawablePadding, 0f)
            }
        }
        super.onDraw(canvas)
    }
}
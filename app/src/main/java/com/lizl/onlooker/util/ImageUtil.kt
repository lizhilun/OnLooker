package com.lizl.onlooker.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.widget.ImageView
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ScreenUtils
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.lizl.onlooker.custom.other.GlideApp

object ImageUtil
{
    private val TAG = "ImageUtil"

    fun displayImage(imageView: ImageView, imageUrl: Any)
    {
        GlideApp.with(imageView).load(imageUrl).into(imageView)
    }

    fun displayOriImage(imageView: ImageView, imageUrl: Any)
    {
        GlideApp.with(imageView).load(imageUrl).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView)
    }

    fun loadImage(context: Context, imageUri: String?, callback: (Bitmap) -> Unit)
    {
        GlideApp.with(context).asBitmap().load(imageUri).into(object : SimpleTarget<Bitmap>()
        {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?)
            {
                callback.invoke(resource)
            }
        })
    }

    /**
     * 调整bitmap到指定的大小
     */
    fun bitmapScale(bitmap: Bitmap, outputWidth: Int, outputHeight: Int): Bitmap
    {
        val oriWidth = bitmap.width
        val oriHeight = bitmap.height
        val scaleWidth = (outputWidth.toFloat()) / bitmap.width
        val scaleHeight = (outputHeight.toFloat()) / bitmap.height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, oriWidth, oriHeight, matrix, true)
    }

    /**
     * 根据指定的最大/最小长宽自适应调整bitmap大小
     */
    fun bitmapAdaptive(bitmap: Bitmap, maxWidth: Int, maxHeight: Int, minWidth: Int, minHeight: Int): Bitmap
    {
        val imageWidth = bitmap.width
        val imageHeight = bitmap.height
        var adaptiveWidth = imageWidth
        var adaptiveHeight = imageHeight
        val imageScale = imageWidth.toFloat() / imageHeight.toFloat()
        val maxScale = maxWidth.toFloat() / maxHeight.toFloat()
        val minScale = minWidth.toFloat() / minHeight.toFloat()

        if (imageWidth < minWidth && imageHeight < minHeight)
        {
            if (imageScale > minScale)
            {
                adaptiveWidth = minWidth
                adaptiveHeight = (adaptiveWidth / imageScale).toInt()
            }
            else
            {
                adaptiveHeight = minHeight
                adaptiveWidth = (adaptiveHeight * imageScale).toInt()
            }
        }

        if (imageWidth > maxWidth)
        {
            if (imageHeight > maxHeight)
            {
                if (imageScale > maxScale)
                {
                    adaptiveWidth = maxWidth
                    adaptiveHeight = (adaptiveWidth / imageScale).toInt()
                }
                else
                {
                    adaptiveHeight = maxHeight
                    adaptiveWidth = (adaptiveHeight * imageScale).toInt()
                }
            }
            else
            {
                adaptiveWidth = maxWidth
                adaptiveHeight = (adaptiveWidth / imageScale).toInt()
            }
        }
        else if (imageHeight > maxHeight)
        {
            adaptiveHeight = maxHeight
            adaptiveWidth = (adaptiveHeight * imageScale).toInt()
        }

        return bitmapScale(bitmap, adaptiveWidth, adaptiveHeight)
    }

    /**
     * 是否为Gif图片
     */
    fun isGifImage(imagePath: String): Boolean
    {
        return FileUtils.getFileExtension(imagePath) == "gif"
    }

    /**
     * 是否为长图
     */
    fun isLargeImage(imageWidth: Int, imageHeight: Int): Boolean
    {
        return (imageHeight.toFloat() / imageWidth > ScreenUtils.getScreenHeight().toFloat() / ScreenUtils.getScreenWidth())
    }
}
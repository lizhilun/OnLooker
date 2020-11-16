package com.lizl.onlooker.custom.other

import android.content.Context
import com.blankj.utilcode.util.PathUtils
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlobalGlideConfiguration : AppGlideModule()
{
    private val DISK_CACHE_SIZE = 1024 * 1024 * 1000L //最多可以缓存多少字节的数据
    private val DISK_CACHE_NAME = "glide"

    override fun applyOptions(context: Context, builder: GlideBuilder)
    {
        //1.设置Glide内存缓存大小
        val maxMemory = Runtime.getRuntime().maxMemory().toInt() //获取系统分配给应用的总内存大小
        val memoryCacheSize = maxMemory / 8 //设置图片内存缓存占用八分之一
        //设置内存缓存大小
        builder.setMemoryCache(LruResourceCache(memoryCacheSize.toLong()))

        //3.设置磁盘缓存大小
        builder.setDiskCache(DiskLruCacheFactory(PathUtils.getExternalAppCachePath(), DISK_CACHE_NAME, DISK_CACHE_SIZE))

        //3.设置BitmapPool缓存内存大小
        builder.setBitmapPool(LruBitmapPool(memoryCacheSize.toLong()))
    }

    override fun isManifestParsingEnabled() = false
}
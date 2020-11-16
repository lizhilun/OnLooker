package com.lizl.onlooker.util

import android.util.Log
import com.blankj.utilcode.util.GsonUtils
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.*


object HttpUtil
{
    private val TAG = "HttpUtil"

    private val httpClient by lazy {
        OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) //设置超时时间
            .readTimeout(5, TimeUnit.SECONDS) //设置读取超时时间
            .writeTimeout(5, TimeUnit.SECONDS) //设置写入超时时间
            .build()
    }

    fun <T> requestData(url: String, type: Class<T>, paramsMap: Map<String, String> = mapOf()): T?
    {
        try
        {
            val result = requestData(url, paramsMap) ?: return null
            return GsonUtils.fromJson(result, type)
        }
        catch (e: Exception)
        {
            Log.e(TAG, "requestData error:", e)
        }
        return null
    }

    private fun requestData(url: String, paramsMap: Map<String, String> = mapOf()): String?
    {
        Log.d(TAG, "requestData() called with: url = [$url]")
        return try
        {
            val urlBuilder = HttpUrl.parse(url)?.newBuilder() ?: return null
            paramsMap.forEach { (t, u) -> urlBuilder.addQueryParameter(t, u) }

            val request: Request = Request.Builder().url(urlBuilder.build()).build()
            val response = httpClient.newCall(request).execute()
            val body = response.body()?.string()
            Log.d(TAG, "requestData body = [$body]")
            if (response.isSuccessful) body else null
        }
        catch (e: Exception)
        {
            Log.e(TAG, "requestData error:", e)
            null
        }
    }
}
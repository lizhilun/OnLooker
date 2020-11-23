package com.lizl.onlooker.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.view.View
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.google.gson.reflect.TypeToken
import com.lizl.onlooker.R
import com.lizl.onlooker.constant.AppConstant
import com.lizl.onlooker.mvvm.model.weibo.WbModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.*

object WBUtil
{
    private val atPattern = Pattern.compile(AppConstant.AT_PATTERN_REGEX)
    private val topicPattern = Pattern.compile(AppConstant.TOPIC_PATTERN_REGEX)
    private val urlPattern = Pattern.compile(AppConstant.URL_PATTERN_REGEX)
    private val emotionPattern = Pattern.compile(AppConstant.EMOTION_PATTERN_REGEX)
    private val highlightTextColor = ColorUtils.getColor(R.color.colorPrimary)

    private const val KEY_WB_CACHE = "KEY_WB_CACHE"

    fun saveWBCache(wbList: List<WbModel>)
    {
        GlobalScope.launch {
            val saveWbList = wbList.subList(0, 20)
            SPUtils.getInstance().put(KEY_WB_CACHE, GsonUtils.toJson(saveWbList))
        }
    }

    fun getCacheWBList(): List<WbModel>
    {
        return try
        {
            val cacheInfo = SPUtils.getInstance().getString(KEY_WB_CACHE)
            GsonUtils.fromJson(cacheInfo, object : TypeToken<ArrayList<WbModel>>()
            {}.type)
        }
        catch (e: Exception)
        {
            emptyList()
        }
    }

    /**
     * 处理微博发送时间
     */
    fun handleWBTime(releaseTime: String): String
    {
        val todaySDF = "HH:mm"
        val yesterdaySDF = StringUtils.getString(R.string.yesterday) + " HH:mm"
        val beforeYesterdaySDF = StringUtils.getString(R.string.the_day_before_yesterday) + " HH:mm"
        val otherDaySDF = "MM-dd HH:mm"
        val otherYearSDF = "yyyy-MM-dd HH:mm"
        val sfd: SimpleDateFormat?

        val WeiBo_ITEM_DATE_FORMAT = "EEE MMM d HH:mm:ss Z yyyy"
        val weiDateFormat = SimpleDateFormat(WeiBo_ITEM_DATE_FORMAT, Locale.US)
        val oldDate = weiDateFormat.parse(releaseTime) ?: return releaseTime

        var result = ""

        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = oldDate
        val now = Date(System.currentTimeMillis())
        val todayCalendar = Calendar.getInstance()
        todayCalendar.time = now
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0)
        todayCalendar.set(Calendar.MINUTE, 0)

        val sameYear = dateCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)

        if (dateCalendar.after(todayCalendar))
        { // 判断是不是今天
            sfd = SimpleDateFormat(todaySDF, Locale.getDefault())
            result = sfd.format(oldDate)
            return result;
        }
        else
        {
            todayCalendar.add(Calendar.DATE, -1)
            if (dateCalendar.after(todayCalendar))
            { // 判断是不是昨天
                sfd = SimpleDateFormat(yesterdaySDF, Locale.getDefault())
                result = sfd.format(oldDate)
                return result
            }
            todayCalendar.add(Calendar.DATE, -2)
            if (dateCalendar.after(todayCalendar))
            { // 判断是不是前天
                sfd = SimpleDateFormat(beforeYesterdaySDF, Locale.getDefault())
                result = sfd.format(oldDate)
                return result
            }
        }

        if (sameYear)
        {
            sfd = SimpleDateFormat(otherDaySDF, Locale.getDefault())
            result = sfd.format(oldDate)
        }
        else
        {
            sfd = SimpleDateFormat(otherYearSDF, Locale.getDefault())
            result = sfd.format(oldDate)
        }

        return result
    }

    /**
     * 处理微博文本
     */
    fun handleWeiboText(weiboText: String, nickName: String?): SpannableString
    {
        val text = if (TextUtils.isEmpty(nickName))
        {
            weiboText
        }
        else
        {
            "$nickName：$weiboText"
        }

        // 处理微博正文
        val spannable = SpannableString(text)

        // 处理昵称
        if (!TextUtils.isEmpty(nickName))
        {
            spannable.setSpan(object : HighlightSpan()
            {
                override fun onClick(p0: View)
                {

                }
            }, 0, nickName!!.length + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }

        // 处理话题
        val topic = topicPattern.matcher(spannable)
        while (topic.find())
        {
            val topicNameMatch = topic.group()
            val start = topic.start()
            spannable.setSpan(object : HighlightSpan()
            {
                override fun onClick(p0: View)
                {

                }
            }, start, start + topicNameMatch.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }

        // 处理@
        val at = atPattern.matcher(spannable)
        while (at.find())
        {
            val atUserName = at.group()
            val start = at.start()
            spannable.setSpan(object : HighlightSpan()
            {
                override fun onClick(p0: View)
                {

                }
            }, start, start + atUserName.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }

        // 处理链接
        val url = urlPattern.matcher(spannable)
        while (url.find())
        {
            val urlString = url.group()
            val start = url.start()
            spannable.setSpan(object : HighlightSpan()
            {
                override fun onClick(p0: View)
                {

                }
            }, start, start + urlString.length, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }

        // 处理表情
        val emotion = emotionPattern.matcher(spannable)
        while (emotion.find())
        {
            val key = emotion.group() // 获取匹配到的具体字符
            val start = emotion.start() // 匹配字符串的开始位置
            //            val emotionPath = EmotionUtil.instance.getEmotionPath(key)
            //            if (!TextUtils.isEmpty(emotionPath))
            //            {
            //                val options = BitmapFactory.Options()
            //                options.inJustDecodeBounds = true
            //                val scale = (options.outWidth / 32)
            //                options.inJustDecodeBounds = false
            //                options.inSampleSize = scale
            //                val bitmap = BitmapFactory.decodeFile(emotionPath, options)
            //                val imageSize = context.resources.getDimensionPixelSize(R.dimen.global_text_size)
            //                val imageBit = ImageUtil.bitmapScale(bitmap, imageSize, imageSize)
            //
            //                spannable.setSpan(EmotionSpan(context, imageBit), start, start + key.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            //            }
        }
        return spannable
    }

    abstract class HighlightSpan : ClickableSpan()
    {
        override fun updateDrawState(ds: TextPaint)
        {
            ds.color = highlightTextColor
        }
    }

    class EmotionSpan(context: Context, bitmap: Bitmap) : ImageSpan(context, bitmap)
    {
        override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint)
        {
            val fm = paint.fontMetricsInt
            val transY = (y + fm.descent + y + fm.ascent) / 2 - drawable.bounds.bottom / 2
            canvas.save()
            canvas.translate(x, transY.toFloat())
            drawable.draw(canvas)
            canvas.restore()
        }
    }
}
package com.lizl.onlooker.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.Utils
import com.lizl.onlooker.R
import com.lizl.onlooker.custom.function.setOnItemClickListener
import com.lizl.onlooker.mvvm.adapter.ImageGridAdapter
import com.lizl.onlooker.mvvm.model.weibo.WbModel
import com.lizl.onlooker.util.ImageUtil
import com.lizl.onlooker.util.PopupUtil
import com.lizl.onlooker.util.WBUtil
import kotlinx.android.synthetic.main.layout_weibo.view.*

class CustomWBLayout(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle)
{
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    companion object
    {
        private val contentPaddingText = Utils.getApp().resources.getDimensionPixelSize(R.dimen.weibo_item_extra_padding_text)
        private val contentPaddingEdge = Utils.getApp().resources.getDimensionPixelSize(R.dimen.weibo_item_content_padding_edge)

        private val extraLayoutPadding: IntArray = intArrayOf(contentPaddingEdge, 0, contentPaddingEdge, 0)
        private val forwardLayoutPadding: IntArray = intArrayOf(contentPaddingEdge, contentPaddingText, contentPaddingEdge, contentPaddingEdge)

        private val minScreenSize = ScreenUtils.getScreenHeight().coerceAtMost(ScreenUtils.getAppScreenWidth())
        private val maxImageSize = minScreenSize / 5 * 4
        private val minImageSize = minScreenSize / 5 * 3
    }

    init
    {
        LayoutInflater.from(context).inflate(R.layout.layout_weibo, null).apply { addView(this) }
    }

    fun bindWBModel(wbModel: WbModel)
    {
        ImageUtil.displayImage(iv_avatar, wbModel.user?.avatarHd.orEmpty())

        tv_content.text = WBUtil.handleWeiboText(wbModel.text, "")
        tv_nickname.text = wbModel.user?.name
        tv_release_time.text = WBUtil.handleWBTime(wbModel.createdAt)

        tv_forward.setText(wbModel.repostsCount.toString())
        tv_comment.setText(wbModel.commentsCount.toString())
        tv_like.setText(wbModel.attitudesCount.toString())

        // 处理图片
        val imageList = wbModel.getPreviewImageList()
        val oriImageList = wbModel.getOriImageList()
        civ_image.isVisible = false
        rv_image_list.isVisible = false
        when
        {
            imageList.size == 1 ->
            {
                civ_image.isVisible = true
                civ_image.bindImage(imageList[0], maxImageSize, minImageSize)
                civ_image.setOnClickListener { PopupUtil.showImageViewerPopup(oriImageList[0]) }
            }
            imageList.size > 1  ->
            {
                rv_image_list.isVisible = true
                rv_image_list.adapter = ImageGridAdapter(imageList.toMutableList()).apply {
                    setOnItemClickListener { it -> PopupUtil.showImageViewerPopup(oriImageList.toMutableList(), imageList.indexOf(it)) }
                }
            }
        }

        // 处理转发
        if (wbModel.forwardWB != null)
        {
            tv_forward_content.isVisible = true
            cl_extra_layout.setPadding(forwardLayoutPadding[0], forwardLayoutPadding[1], forwardLayoutPadding[2], forwardLayoutPadding[3])
            cl_extra_layout.setBackgroundResource(R.color.colorForwardWBBg)
            tv_forward_content.text = WBUtil.handleWeiboText(wbModel.forwardWB?.text.orEmpty(), wbModel.forwardWB?.user?.name)
        }
        else
        {
            tv_forward_content.isVisible = false
            cl_extra_layout.setPadding(extraLayoutPadding[0], extraLayoutPadding[1], extraLayoutPadding[2], extraLayoutPadding[3])
            cl_extra_layout.setBackgroundResource(R.color.transparent)
        }
    }
}
package com.lizl.onlooker.custom.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.blankj.utilcode.util.Utils
import com.lizl.onlooker.R
import com.lizl.onlooker.mvvm.adapter.ImageGridAdapter
import com.lizl.onlooker.mvvm.model.weibo.WbModel
import com.lizl.onlooker.util.ImageUtil
import com.lizl.onlooker.util.WBUtil
import kotlinx.android.synthetic.main.layout_weibo.view.*

class CustomWBLayout(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attrs, defStyle)
{
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    companion object
    {
        private val extraLayoutPaddingEdge = Utils.getApp().resources.getDimensionPixelSize(R.dimen.weibo_item_content_padding_edge)
        private val forwardLayoutPaddingContent = Utils.getApp().resources.getDimensionPixelSize(R.dimen.weibo_item_forward_content_padding_content)

        private val extraLayoutPadding: IntArray = intArrayOf(extraLayoutPaddingEdge, 0, extraLayoutPaddingEdge, 0)
        private val forwardLayoutPadding: IntArray =
                intArrayOf(extraLayoutPaddingEdge, forwardLayoutPaddingContent, extraLayoutPaddingEdge, forwardLayoutPaddingContent)
    }

    init
    {
        val contentView = LayoutInflater.from(context).inflate(R.layout.layout_weibo, null)
        addView(contentView)
    }

    fun bindWBModel(wbModel: WbModel)
    {
        ImageUtil.displayImage(iv_avatar, wbModel.user?.avatarHd.orEmpty())

        tv_content.text = WBUtil.handleWeiboText(wbModel.text, "")
        tv_nickname.text = wbModel.user?.name
        tv_release_time.text = WBUtil.handleWBTime(wbModel.createdAt)

        tv_forward.text = wbModel.repostsCount.toString()
        tv_comment.text = wbModel.commentsCount.toString()
        tv_like.text = wbModel.attitudesCount.toString()

        // 处理图片
        val imageList = wbModel.getPreviewImageList()
        rv_image_list.isVisible = imageList.isNotEmpty()
        if (imageList.isNotEmpty())
        {
            rv_image_list.isVisible = true
            rv_image_list.adapter = ImageGridAdapter(imageList.toMutableList())
        }

        // 处理转发
        if (wbModel.forwardWB != null)
        {
            tv_forward_content.isVisible = true
            cl_extra_layout.setPadding(forwardLayoutPadding[0], forwardLayoutPadding[1], forwardLayoutPadding[2], forwardLayoutPadding[3])
            cl_extra_layout.setBackgroundColor(context.getColor(R.color.colorForwardWBBg))
            tv_forward_content.text = WBUtil.handleWeiboText(wbModel.forwardWB?.text.orEmpty(), wbModel.forwardWB?.user?.name)
        }
        else
        {
            tv_forward_content.isVisible = false
            cl_extra_layout.setPadding(extraLayoutPadding[0], extraLayoutPadding[1], extraLayoutPadding[2], extraLayoutPadding[3])
        }
    }
}
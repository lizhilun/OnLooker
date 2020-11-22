package com.lizl.onlooker.custom.popup

import android.content.Context
import androidx.core.view.isVisible
import com.blankj.utilcode.util.StringUtils
import com.lizl.onlooker.R
import com.lizl.onlooker.mvvm.adapter.ImagePagerAdapter
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.impl.FullScreenPopupView
import com.lizl.onlooker.custom.function.registerOnPageChangeCallback
import com.lizl.onlooker.mvvm.model.other.OperationModel
import com.lizl.onlooker.util.DialogUtil
import com.lizl.onlooker.util.ImageUtil
import kotlinx.android.synthetic.main.popup_image_viewer.view.*

class PopupImageViewer(context: Context, private val imageList: MutableList<String>, private val position: Int) : FullScreenPopupView(context)
{
    override fun getImplLayoutId() = R.layout.popup_image_viewer

    override fun onCreate()
    {
        popupInfo?.let {
            it.hasShadowBg = true
            it.hasStatusBar = false
            it.popupAnimation = PopupAnimation.ScaleAlphaFromCenter
        }

        val imagePagerAdapter = ImagePagerAdapter(imageList)
        rv_image_pager.adapter = imagePagerAdapter
        tv_indicator.bringToFront()

        rv_image_pager.setCurrentItem(position, false)

        tv_indicator.isVisible = imageList.size > 1
        tv_indicator.text = "${position + 1}/${imageList.size}"

        rv_image_pager.registerOnPageChangeCallback { position ->
            tv_indicator.text = "${position + 1}/${imageList.size}"
        }

        imagePagerAdapter.setOnOutsidePhotoTapListener { dismiss() }
        imagePagerAdapter.setOnImageLongClickListener {
            DialogUtil.showOperationDialog(mutableListOf<OperationModel>().apply {
                add(OperationModel(StringUtils.getString(R.string.save_image)) { ImageUtil.saveImage(it) })
                add(OperationModel(StringUtils.getString(R.string.save_all_image)) { imageList.forEach { ImageUtil.saveImage(it) } })
            })
        }
    }
}
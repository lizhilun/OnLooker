package com.lizl.onlooker.mvvm.adapter

import android.widget.ImageView.ScaleType.FIT_CENTER
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.blankj.utilcode.util.Utils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.onlooker.R
import com.lizl.onlooker.databinding.ItemImageGridBinding
import com.lizl.onlooker.util.ImageUtil

class ImageGridAdapter(imageList: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image_grid, imageList)
{
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemImageGridBinding>(viewHolder.itemView)
    }

    companion object
    {
        private val maxImageSize = Utils.getApp().resources.getDimensionPixelSize(R.dimen.weibo_item_image_max_size)
        private val minImageSize = Utils.getApp().resources.getDimensionPixelSize(R.dimen.weibo_item_image_min_size)
    }


    override fun convert(helper: BaseViewHolder, item: String)
    {
        helper.getBinding<ItemImageGridBinding>()?.apply {

            this.ivImageTypeLong.isVisible = false
            this.ivImageTypeGif.isVisible = ImageUtil.isGifImage(item)

            ImageUtil.loadImage(this.ivImage, item) { bitmap ->

                this.ivImageTypeLong.isVisible = ImageUtil.isLargeImage(bitmap.width, bitmap.height)

                if (data.size == 1)
                {
                    this.ivImage.scaleType = FIT_CENTER
                    this.ivImage.setImageBitmap(ImageUtil.bitmapAdaptive(bitmap, maxImageSize, maxImageSize, minImageSize, minImageSize))
                }

                executePendingBindings()
            }
        }
    }
}
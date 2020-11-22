package com.lizl.onlooker.mvvm.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.onlooker.R
import com.lizl.onlooker.databinding.ItemImageGridBinding

class ImageGridAdapter(imageList: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image_grid, imageList)
{
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemImageGridBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: String)
    {
        helper.getBinding<ItemImageGridBinding>()?.apply {

            imageUrl = item

            executePendingBindings()
        }
    }
}
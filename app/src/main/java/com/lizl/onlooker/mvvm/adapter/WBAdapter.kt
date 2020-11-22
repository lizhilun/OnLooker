package com.lizl.onlooker.mvvm.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.onlooker.R
import com.lizl.onlooker.custom.other.CustomDiffUtil
import com.lizl.onlooker.databinding.ItemWeiboBinding
import com.lizl.onlooker.mvvm.model.weibo.WbModel

class WBAdapter : BaseQuickAdapter<WbModel, BaseViewHolder>(R.layout.item_weibo), LoadMoreModule
{
    init
    {
        setDiffCallback(CustomDiffUtil({ oldItem, newItem -> oldItem.id == newItem.id }, { oldItem, newItem -> oldItem == newItem }))
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemWeiboBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: WbModel)
    {
        helper.getBinding<ItemWeiboBinding>()?.apply {
            wbModel = item
            executePendingBindings()
        }
    }
}
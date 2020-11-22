package com.lizl.onlooker.mvvm.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.onlooker.R
import com.lizl.onlooker.databinding.ItemOperationBinding
import com.lizl.onlooker.mvvm.model.other.OperationModel

class OperationListAdapter(operationList: MutableList<OperationModel>) :
        BaseQuickAdapter<OperationModel, BaseViewHolder>(R.layout.item_operation, operationList)
{
    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemOperationBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: OperationModel)
    {
        helper.getBinding<ItemOperationBinding>()?.apply {
            operationModel = item
            executePendingBindings()
        }
    }
}
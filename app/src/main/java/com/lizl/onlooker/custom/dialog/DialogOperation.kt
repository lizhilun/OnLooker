package com.lizl.onlooker.custom.dialog

import android.content.Context
import com.lizl.onlooker.R
import com.lizl.onlooker.custom.function.setOnItemClickListener
import com.lizl.onlooker.mvvm.adapter.OperationListAdapter
import com.lizl.onlooker.mvvm.model.other.OperationModel
import kotlinx.android.synthetic.main.dialog_operation.*

class DialogOperation(context: Context, private val operationList: MutableList<OperationModel>) : BaseDialog(context, R.layout.dialog_operation)
{
    override fun initView()
    {
        rv_operation_list.adapter = OperationListAdapter(operationList).apply {
            setOnItemClickListener { it -> it.callback.invoke() }
        }
    }
}
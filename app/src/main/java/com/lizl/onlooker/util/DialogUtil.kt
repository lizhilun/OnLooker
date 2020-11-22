package com.lizl.onlooker.util

import android.app.Dialog
import com.blankj.utilcode.util.ActivityUtils
import com.lizl.onlooker.custom.dialog.DialogOperation
import com.lizl.onlooker.mvvm.model.other.OperationModel

object DialogUtil
{
    private var dialog: Dialog? = null

    fun showOperationDialog(operationList: MutableList<OperationModel>)
    {
        val context = ActivityUtils.getTopActivity() ?: return
        showDialog(DialogOperation(context, operationList))
    }

    private fun showDialog(dialog: Dialog)
    {
        this.dialog?.dismiss()
        this.dialog = dialog
        this.dialog?.show()
    }
}
package com.lizl.onlooker.mvvm.model.weibo

import com.google.gson.annotations.SerializedName

class WBRequestResponseModel
{
    var statuses: List<WbModel>? = null

    var advertises: List<String>? = null

    var ad: List<String>? = null

    var hasvisible = false

    @SerializedName("previous_cursor")
    var previousCursor = 0L

    @SerializedName("previous_cursor_str")
    var previousCursorStr = ""

    @SerializedName("total_number")
    var totalNumber = 0

    var interval = 0

    @SerializedName("uve_blank")
    var uveBlank = 0

    @SerializedName("since_id")
    var sinceId = 0L

    @SerializedName("since_id_str")
    var sinceIdStr = ""

    @SerializedName("max_id")
    var maxId = 0L

    @SerializedName("max_id_str")
    var maxIdStr = ""

    @SerializedName("has_unread")
    var hasUnread = 0
}
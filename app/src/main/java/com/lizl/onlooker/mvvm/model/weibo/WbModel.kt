package com.lizl.onlooker.mvvm.model.weibo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class WbModel
{
    var visible: WBVisibleModel? = null

    @SerializedName("created_at")
    var createdAt = ""

    var id = 0L

    @SerializedName("idstr")
    var idStr = ""

    var mid = 0L

    @SerializedName("can_edit")
    var canEdit = false

    @SerializedName("show_additional_indication")
    var showAdditionalIndication = 0

    var text = ""

    var textLength = 0

    @SerializedName("source_allowclick")
    var sourceAllowClick = 0

    @SerializedName("source_type")
    var sourceType = 0

    var source = ""

    var favorited = false

    var truncated = false

    @SerializedName(value = "retweeted_status")
    var forwardWB: WbModel? = null

    @SerializedName("in_reply_to_status_id")
    var inReplyToStatusId = ""

    @SerializedName("in_reply_to_user_id")
    var inReplyToUserId = ""

    @SerializedName("in_reply_to_screen_name")
    var inReplyToScreenName = ""

    @SerializedName("pic_urls")
    var picUrls: List<WBThumbnailPicModel>? = null

    var geo = ""

    var user: WBUserModel? = null

    @SerializedName("is_paid")
    var isPaid = false

    @SerializedName("mblog_vip_type")
    var mBlogVipType = 0

    @SerializedName("reposts_count")
    var repostsCount = 0

    @SerializedName("comments_count")
    var commentsCount = 0

    @SerializedName("attitudes_count")
    var attitudesCount = 0

    @SerializedName("pending_approval_count")
    var pendingApprovalCount = 0

    var isLongText = false

    // 缩略图片地址，没有时不返回此字段
    @Expose(deserialize = false, serialize = false)
    private var thumbanilList = ArrayList<String>()

    // 中等尺寸图片地址，没有时不返回此字段
    @Expose(deserialize = false, serialize = false)
    private var bmiddleList = ArrayList<String>()

    // 原始图片地址，没有时不返回此字段
    @Expose(deserialize = false, serialize = false)
    private var oriImageList = ArrayList<String>()

    private fun handleImage()
    {
        if (picUrls.isNullOrEmpty()) return

        picUrls?.forEach {
            thumbanilList.add(it.thumbnailPic)
            bmiddleList.add(it.thumbnailPic.replace("thumbnail", "bmiddle"))
            if (!it.thumbnailPic.endsWith(".gif"))
            {
                oriImageList.add(it.thumbnailPic.replace("thumbnail", "large"))
            }
            else
            {
                oriImageList.add(it.thumbnailPic.replace("thumbnail", "bmiddle"))
            }
        }
    }

    fun getPreviewImageList(): List<String>
    {
        if (bmiddleList.isEmpty())
        {
            handleImage()
        }
        return bmiddleList
    }

    fun getOriImageList(): List<String>
    {
        if (oriImageList.isEmpty())
        {
            handleImage()
        }
        return oriImageList
    }
}

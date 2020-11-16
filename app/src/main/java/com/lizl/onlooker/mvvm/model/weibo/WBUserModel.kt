package com.lizl.onlooker.mvvm.model.weibo

import com.google.gson.annotations.SerializedName

class WBUserModel
{
    var id = 0L

    @SerializedName("idstr")
    var idStr = ""

    @SerializedName("screen_name")
    var screenName = ""

    var name = ""

    var province = ""

    var city = 0

    var location = ""

    var description = ""

    var url = ""

    @SerializedName("profile_image_url")
    var profileImageUrl = ""

    @SerializedName("cover_image")
    var coverImage = ""

    @SerializedName("cover_image_phone")
    var coverImagePhone = ""

    @SerializedName("profile_url")
    var profileUrl = ""

    var domain = ""

    var weihao = ""

    var gender = ""

    @SerializedName("followers_count")
    var followersCount = 0

    @SerializedName("friends_count")
    var friendsCount = 0

    @SerializedName("pagefriends_count")
    var pagefriendsCount = 0

    @SerializedName("statuses_count")
    var statusesCount = 0

    @SerializedName("video_status_count")
    var videoStatusCount = 0

    @SerializedName("video_play_count")
    var videoPlayCount = 0

    @SerializedName("favourites_count")
    var favouritesCount = 0

    @SerializedName("created_at")
    var createdAt = ""

    var following = false

    @SerializedName("allow_all_act_msg")
    var allowAllActMsg = false

    @SerializedName("geo_enabled")
    var geoEnabled = false

    var verified = false

    @SerializedName("verified_type")
    var verifiedType = 0

    var remark = ""

    @SerializedName("allow_all_comment")
    var allowAllComment = false

    @SerializedName("avatar_large")
    var avatarLarge = ""

    @SerializedName("avatar_hd")
    var avatarHd = ""

    @SerializedName("follow_me")
    var followMe = false

    var like = false

    @SerializedName("like_me")
    var likeMe = false

    @SerializedName("online_status")
    var onlineStatus = 0

    @SerializedName("special_follow")
    var specialFollow = false
}
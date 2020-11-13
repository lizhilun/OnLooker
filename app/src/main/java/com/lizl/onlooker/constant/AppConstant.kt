package com.lizl.onlooker.constant

object AppConstant
{
    /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY  */
    const val APP_KEY = "379872536"

    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     */
    const val REDIRECT_URL = "https://api.weibo.com/oauth2/default.html"

    /**
     * WeiboSDKDemo 应用对应的权限，第三方开发者一般不需要这么多，可直接设置成空即可。
     * 详情请查看 Demo 中对应的注释。
     */
    const val SCOPE = ""

    const val BUNDLE_DATA_INT = "BUNDLE_DATA_INT"
    const val BUNDLE_DATA_SERIALIZABLE = "BUNDLE_DATA_SERIALIZABLE"
}
package com.lizl.onlooker.util

import com.blankj.utilcode.util.Utils
import com.sina.weibo.sdk.auth.AccessTokenKeeper
import com.sina.weibo.sdk.auth.Oauth2AccessToken

object AccessTokenUtil
{
    private var mAccessToken: Oauth2AccessToken? = null

    init
    {
        mAccessToken = AccessTokenKeeper.readAccessToken(Utils.getApp())
    }

    fun saveAccessToken(token: Oauth2AccessToken)
    {
        if (!token.isSessionValid)
        {
            return
        }
        mAccessToken = token
        AccessTokenKeeper.writeAccessToken(Utils.getApp(), token)
    }

    fun isTokenSessionValid() = mAccessToken?.isSessionValid == true
}
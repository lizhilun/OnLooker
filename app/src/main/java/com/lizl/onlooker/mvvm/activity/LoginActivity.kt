package com.lizl.onlooker.mvvm.activity

import android.content.Intent
import android.util.Log
import com.blankj.utilcode.util.ActivityUtils
import com.lizl.onlooker.R
import com.lizl.onlooker.databinding.ActivityLoginBinding
import com.lizl.onlooker.mvvm.base.BaseActivity
import com.lizl.onlooker.util.AccessTokenUtil
import com.sina.weibo.sdk.auth.Oauth2AccessToken
import com.sina.weibo.sdk.auth.WbAuthListener
import com.sina.weibo.sdk.auth.WbConnectErrorMessage
import com.sina.weibo.sdk.auth.sso.SsoHandler

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login)
{
    private val mSsoHandler: SsoHandler by lazy { SsoHandler(this) }

    override fun initData()
    {
        mSsoHandler.authorize(object : WbAuthListener
        {
            override fun onSuccess(token: Oauth2AccessToken?)
            {
                token ?: return
                AccessTokenUtil.saveAccessToken(token)
                ActivityUtils.startActivity(MainActivity::class.java)
            }

            override fun cancel()
            {
                Log.d(TAG, "cancel() called")
            }

            override fun onFailure(error: WbConnectErrorMessage?)
            {
                Log.d(TAG, "onFailure() called with: p0 = [${error?.errorMessage}]")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        mSsoHandler.authorizeCallBack(requestCode, resultCode, data)
    }
}
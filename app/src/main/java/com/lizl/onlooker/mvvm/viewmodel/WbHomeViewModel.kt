package com.lizl.onlooker.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import com.lizl.onlooker.mvvm.model.weibo.WBRequestResponseModel
import com.lizl.onlooker.mvvm.model.weibo.WbModel
import com.lizl.onlooker.util.AccessTokenUtil
import com.lizl.onlooker.util.HttpUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WbHomeViewModel : ViewModel()
{
    val wbListLiveData = MutableLiveData<MutableList<WbModel>>()

    fun requestData()
    {
        GlobalScope.launch {
            val response = HttpUtil.requestData("https://api.weibo.com/2/statuses/home_timeline.json", WBRequestResponseModel::class.java,
                    mapOf("access_token" to AccessTokenUtil.getAccessToken()?.token.orEmpty()))
            wbListLiveData.postValue(response?.statuses.orEmpty().toMutableList())
        }
    }
}
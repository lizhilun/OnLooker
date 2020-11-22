package com.lizl.onlooker.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
            val wbList = requestWBData().sortedByDescending { it.id }.toMutableList()
            wbListLiveData.postValue(wbList)
        }
    }

    fun refreshMoreData()
    {
        GlobalScope.launch {
            val curWbList = wbListLiveData.value.orEmpty().toMutableList()
            val maxId = curWbList.maxByOrNull { it.id }?.id ?: -1
            val newWbList = requestWBData(mapOf("since_id" to (maxId + 1).toString()))
            curWbList.addAll(newWbList)
            curWbList.sortByDescending { it.id }
            wbListLiveData.postValue(curWbList)
        }
    }

    fun loadMoreData()
    {
        GlobalScope.launch {
            val curWbList = wbListLiveData.value.orEmpty().toMutableList()
            val minId = curWbList.minByOrNull { it.id }?.id ?: 1
            val newWbList = requestWBData(mapOf("max_id" to (minId - 1).toString()))
            curWbList.addAll(newWbList)
            curWbList.sortByDescending { it.id }
            wbListLiveData.postValue(curWbList)
        }
    }

    private fun requestWBData(paramsMap: Map<String, String> = mapOf()): List<WbModel>
    {
        val useParamsMap = mutableMapOf<String, String>().apply {
            put("access_token", AccessTokenUtil.getAccessToken()?.token.orEmpty())
            paramsMap.forEach { (t, u) -> put(t, u) }
        }
        return HttpUtil.requestData("https://api.weibo.com/2/statuses/home_timeline.json", WBRequestResponseModel::class.java, useParamsMap)?.statuses.orEmpty()
    }
}
package com.lizl.onlooker.mvvm.base

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.lizl.onlooker.R
import com.lizl.onlooker.util.SkinUtil

open class BaseActivity<DB : ViewDataBinding>(private val layoutId: Int) : AppCompatActivity()
{
    protected val TAG = this.javaClass.simpleName

    protected lateinit var dataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?)
    {
        Log.d(TAG, "onCreate")
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, layoutId)
        dataBinding.lifecycleOwner = this

        updateStatusBarColor()

        initView()
        initData()
    }

    private fun updateStatusBarColor()
    {
        BarUtils.setStatusBarColor(this, SkinUtil.getColor(this, R.color.colorContentBg))
    }

    override fun onResume()
    {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onStart()
    {
        Log.d(TAG, "onStart")
        super.onStart()
    }

    override fun onRestart()
    {
        Log.d(TAG, "onRestart")
        super.onRestart()
    }

    override fun onPause()
    {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onStop()
    {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onDestroy()
    {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    open fun initView()
    {

    }

    open fun initData()
    {

    }

    /**
     * 点击EditText外隐藏输入法
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean
    {
        if (ev.action == MotionEvent.ACTION_DOWN)
        {
            if (isShouldHideKeyboard(currentFocus, ev))
            {
                KeyboardUtils.hideSoftInput(this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean
    {
        if (v is EditText)
        {
            val location = IntArray(2)
            v.getLocationInWindow(location)
            val left = location[0]
            val top = location[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }
}
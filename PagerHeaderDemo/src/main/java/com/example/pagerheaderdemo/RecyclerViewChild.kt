package com.example.pagerheaderdemo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import hugo.weaving.DebugLog

// Created by luckyzhangx on 2018/12/18.
@DebugLog
class RecyclerViewChild : RecyclerView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            stopScroll()
            parent.requestDisallowInterceptTouchEvent(true)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {

        return super.onTouchEvent(e)
    }
}
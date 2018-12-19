package com.example.pagerheaderdemo

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import hugo.weaving.DebugLog

// Created by luckyzhangx on 2018/12/19.

@DebugLog
class NestedScrollLLM : LinearLayoutManager {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        var scrolledDy = super.scrollVerticallyBy(dy, recycler, state)
        if (dy != scrolledDy) {
            for (i in 0 until childCount) {
                val nest = getChildAt(i).findNestedScrollingChild()
                if (nest != null) {
                    if (nest is RecyclerView) {
                        nest.scrollBy(0, dy - scrolledDy)
                        scrolledDy = dy
                        return scrolledDy
                    }
                }

            }
        }
        return scrolledDy
    }
}
package com.example.pagerheaderdemo

import android.content.Context
import android.support.v4.view.*
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import hugo.weaving.DebugLog

// Created by luckyzhangx on 2018/12/18.
@DebugLog
class RecyclerViewParent : RecyclerView, NestedScrollingParent2 {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context!!, attrs, defStyle)

    // NestedScrollingParent

    override fun getNestedScrollAxes(): Int {
        return mParentHelper.getNestedScrollAxes()
    }

    override fun startNestedScroll(axes: Int): Boolean {
        Log.d("parent", "dispatchNestedScroll")
        return super.startNestedScroll(axes)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        Log.d("parent", "dispatchNestedScroll")
        return super.startNestedScroll(axes, type)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("parent", "dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        if (scrollState == RecyclerView.SCROLL_STATE_SETTLING && (e?.action == MotionEvent.ACTION_DOWN)) {
            stopScroll()
        }

        return super.onInterceptTouchEvent(e)
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes)
        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        mParentHelper.onStopNestedScroll(target)
        stopNestedScroll()
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?, type: Int): Boolean {
        if (dyUnconsumed > 0 && layoutManager?.isAtBottom() == true) {
            val nest = findNestedScrollingChild()
//                    ?.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
            if (nest is RecyclerView) {
                nest.scrollBy(dxUnconsumed, dyUnconsumed)
            }
            return true
        }
        return super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type)
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        val oldScrollY = scrollY
        if (layoutManager?.isAtBottom() == true && dyUnconsumed > 0) {
            dispatchNestedScroll(0, 0, 0, dyUnconsumed, null)
            return
        }
        if (dyUnconsumed < 0) {
            scrollBy(0, dyUnconsumed)
        }
        val myConsumed = scrollY - oldScrollY
        val myUnconsumed = dyUnconsumed - myConsumed
        dispatchNestedScroll(0, myConsumed, 0, myUnconsumed, null)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        //        dispatchNestedPreScroll(dx, dy, consumed, null)
        // scroll up before child scroll up
        if (layoutManager?.isAtBottom() != true && dy > 0) {
            scrollBy(dx, dy)
            consumed[1] = dy
        }
    }


    private var mParentHelper: NestedScrollingParentHelper = NestedScrollingParentHelper(this)
}

fun View.findNestedScrollingChild(): NestedScrollingChild? {
    if (this !is ViewGroup) {
        if (this is NestedScrollingChild) {
            return this
        } else {
            return null
        }
    } else {
        val nest = children.find { it.findNestedScrollingChild() != null }?.findNestedScrollingChild()
        if (nest != null) {
            return nest
        } else {
            if (this is NestedScrollingChild) {
                return this
            } else {
                return null
            }
        }
    }
}

val ViewGroup.children: List<View>
    get() {
        val views = mutableListOf<View>()
        for (i in 0 until childCount) {
            views.add(getChildAt(i))
        }
        return views
    }
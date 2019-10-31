package com.luckyzhangx.coreandroidlibs.recyclerview.sticky

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import org.jetbrains.anko.AnkoLogger

// Created by luckyzhangx on 2019-09-12.
class StickViewInner constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr), AnkoLogger {

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    var stickyView: View? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (stickyView != null && stickyView?.parent != this) {
            val parent = stickyView?.parent as? ViewGroup
            parent?.removeView(stickyView)
            addView(stickyView)
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            removeView(stickyView)
            (parent)?.addView(stickyView)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }
}
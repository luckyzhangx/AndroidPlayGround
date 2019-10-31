package com.luckyzhangx.coreandroidlibs.recyclerview.sticky

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout


// Created by luckyzhangx on 2019-09-12.
class StickViewOuter @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }
}
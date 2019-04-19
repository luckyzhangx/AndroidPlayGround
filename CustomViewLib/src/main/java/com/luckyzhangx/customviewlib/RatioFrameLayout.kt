package com.luckyzhangx.customviewlib

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

// Created by luckyzhangx on 2019/3/18.
/**
 * 自定义宽高比
 * 可设置宽度优先或者高度优先
 * 原理：先走默认 measure 流程，然后根据宽高优先，算出对应的高宽，重新 measure
 */

private val measureBaseWidth = 0
private val measureBaseHeight = 1

class RatioFrameLayout : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        val typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.RatioFrameLayout, defStyleAttr, defStyleRes);

        measureBase = typedArray.getInt(R.styleable.RatioFrameLayout_measureBase, 0)
        ratioHw = typedArray.getFloat(R.styleable.RatioFrameLayout_ratioHw, 0f)
    }

    private var measureBase = 0

    var ratioHw = 0f
        set(value) {
            if (field != value)
                requestLayout()
            field = value
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (ratioHw != 0f) {
            if (measureBase == measureBaseWidth) {
                super.onMeasure(
                        MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec((ratioHw * measuredWidth).toInt(), MeasureSpec.EXACTLY)
                )
            } else {
                super.onMeasure(
                        MeasureSpec.makeMeasureSpec((measuredHeight / ratioHw).toInt(), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY)
                )
            }
        }

    }
}
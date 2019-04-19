package com.luckyzhangx.customviewlib

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

// Created by luckyzhangx on 2019/3/18.

/**
 * 添加水平和垂直方向的伪 margin，布局尺寸比父 View 小，但不增加 margin 影响其他 View 布局
 * 主要是 ViewHolder 使用：希望 ViewHolder 比 RecyclerView 小固定宽高
 */
class RelativeSizeFrameLayout : FrameLayout {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        val typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.RelativeFrameLayout, defStyleAttr, defStyleRes);

        hMargin = typedArray.getDimension(R.styleable.RelativeFrameLayout_margin_horizontal, 0f).toInt()
        vMargin = typedArray.getDimension(R.styleable.RelativeFrameLayout_margin_vertical, 0f).toInt()
    }

    var hMargin = 0
        set(value) {
            if (field != value)
                requestLayout()
            field = value
        }
    var vMargin = 0
        set(value) {
            if (field != value)
                requestLayout()
            field = value
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        var w = MeasureSpec.getSize(widthMeasureSpec)
        var wm = MeasureSpec.getMode(widthMeasureSpec)
        var h = MeasureSpec.getSize(heightMeasureSpec)
        var hm = MeasureSpec.getMode(heightMeasureSpec)

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(
                        ((w - hMargin).toInt().takeIf { it >= 0 } ?: 0), wm),
                MeasureSpec.makeMeasureSpec(
                        ((h - vMargin).toInt().takeIf { it >= 0 } ?: 0), hm))

    }
}
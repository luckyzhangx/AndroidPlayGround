package com.luckyzhangx.customviewlib.dialog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.FrameLayout
import org.jetbrains.anko.dip

// Created by luckyzhangx on 2019/4/18.

/**
 * 使用 [Canvas.clipPath] 将 View 裁切成任意形状
 * todo 优化 dispatchDraw 和 onDraw 的调用问题：这两个方法都可能被调用，同时裁切影响性能
 * todo 任意形状的阴影
 */
class DialogFrameLayout : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun dispatchDraw(canvas: Canvas?) {

        canvas?.apply {
            saveLayer(0f, 0f, width.toFloat(), height.toFloat(), Paint())
        }
        canvas?.clipPath(createPath())
        super.dispatchDraw(canvas)
        canvas?.restore()
    }

    private val radius = dip(6).toFloat()

    private val triLen = dip(8).toFloat()
    private val triWidth = dip(8).toFloat()

    private fun createPath(): Path {
        val path = Path()
        path.arcTo(RectF(0f, 0f,
                radius * 2, radius * 2),
                -180f, 90f)

        path.arcTo(RectF(width - 2 * radius, 0f, width.toFloat(),
                radius * 2), -90f, 90f)

        path.arcTo(RectF(width - 2 * radius, height - 2 * radius - triLen, width.toFloat(), height.toFloat() - triLen), 0f, 90f)

        path.lineTo(width / 2 + triWidth, height.toFloat() - triLen)
        path.lineTo(width / 2f, height.toFloat())
        path.lineTo(width / 2 - triWidth, height.toFloat() - triLen)

        path.arcTo(RectF(0f, height - 2 * radius - triLen,
                radius * 2, height.toFloat() - triLen), 90f, 90f)
        return path
    }
}
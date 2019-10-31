package com.luckyzhangx.coreandroidlibs.gridphotoview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import org.jetbrains.anko.backgroundColor

// Created by luckyzhangx on 2019-10-11.
class PoiCommentGridImgView @JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private val measureDefault = MeasureDefault()
        private val measurerOne = MeasurerOne()
        private val measurerTwo = MeasurerTwo()
        private val measurerThree = MeasurerTree()

        interface IMeasurer {
            fun measureChild(
                    parent: PoiCommentGridImgView,
                    child: View,
                    parentWidthMeasureSpec: Int,
                    parentHeightMeasureSpec: Int,
                    dataMgr: DataMgr<*>? = null)

            fun layoutChildren(parent: PoiCommentGridImgView, left: Int, top: Int, right: Int, bottom: Int, forceLeftGravity: Boolean)
        }

        private class MeasureDefault : IMeasurer {
            override fun measureChild(parent: PoiCommentGridImgView,
                                      child: View, parentWidthMeasureSpec: Int,
                                      parentHeightMeasureSpec: Int,
                                      dataMgr: DataMgr<*>?) {
                parent.superMeasureChildWithMargins(child, parentWidthMeasureSpec, 0, parentHeightMeasureSpec, 0)
            }

            override fun layoutChildren(parent: PoiCommentGridImgView, left: Int, top: Int, right: Int, bottom: Int, forceLeftGravity: Boolean) {
                val count = parent.childCount
                val parentLeft = parent.paddingLeft
                val parentRight = right - left - parent.paddingEnd

                val parentTop = parent.paddingTop
                val parentBottom = bottom - top - parent.paddingBottom

                for (i in 0 until count) {
                    val child = parent.getChildAt(i)
                    parent.defaultLayoutChild(child)
                }

            }
        }

        private class MeasurerOne : IMeasurer {
            override fun measureChild(parent: PoiCommentGridImgView,
                                      child: View,
                                      parentWidthMeasureSpec: Int,
                                      parentHeightMeasureSpec: Int,
                                      dataMgr: DataMgr<*>?) {
                val index = parent.indexOfChild(child)
                val parentChildWidth = MeasureSpec.getSize(parentWidthMeasureSpec) - (parent.paddingLeft + parent.paddingEnd)

                when (index) {
                    0 -> {
                        val width = parentChildWidth * 0.5
                        val widthSpec = MeasureSpec.makeMeasureSpec(width.toInt(), MeasureSpec.EXACTLY)
                        val heightSpec = MeasureSpec.makeMeasureSpec((width * 1.2).toInt(), MeasureSpec.EXACTLY)
                        child.measure(widthSpec, heightSpec)
                    }
                    else -> {
                        parent.superMeasureChildWithMargins(child, parentWidthMeasureSpec, 0, parentHeightMeasureSpec, 0)
                    }
                }

            }

            override fun layoutChildren(parent: PoiCommentGridImgView, left: Int, top: Int, right: Int, bottom: Int, forceLeftGravity: Boolean) {
                val count = parent.childCount
                val parentLeft = parent.paddingLeft
                val parentRight = right - left - parent.paddingEnd

                val parentTop = parent.paddingTop
                val parentBottom = bottom - top - parent.paddingBottom

                for (i in 0 until count) {
                    val child = parent.getChildAt(i)
                    when (i) {
                        0 -> {
                            child.layout(parentLeft, parentTop, parentLeft + child.measuredWidth, parentTop + child.measuredHeight)
                        }
                        else -> {
                            parent.defaultLayoutChild(child)
                        }
                    }
                }

            }
        }

        private class MeasurerTwo : IMeasurer {
            override fun measureChild(parent: PoiCommentGridImgView,
                                      child: View,
                                      parentWidthMeasureSpec: Int,
                                      parentHeightMeasureSpec: Int,
                                      dataMgr: DataMgr<*>?) {
                val index = parent.indexOfChild(child)
                val parentChildWidth = MeasureSpec.getSize(parentWidthMeasureSpec) - (parent.paddingLeft + parent.paddingEnd)

                when (index) {
                    0, 1 -> {
                        val width = (parentChildWidth - parent.dividerWidth) * 0.5
                        val widthSpec = MeasureSpec.makeMeasureSpec(width.toInt(), MeasureSpec.EXACTLY)
                        val heightSpec = MeasureSpec.makeMeasureSpec((width * 5 / 7).toInt(), MeasureSpec.EXACTLY)
                        child.measure(widthSpec, heightSpec)
                    }
                    else -> {
                        parent.superMeasureChildWithMargins(child, parentWidthMeasureSpec, 0, parentHeightMeasureSpec, 0)
                    }
                }

            }

            override fun layoutChildren(parent: PoiCommentGridImgView, left: Int, top: Int, right: Int, bottom: Int, forceLeftGravity: Boolean) {
                val count = parent.childCount
                val parentLeft = parent.paddingLeft
                val parentRight = right - left - parent.paddingEnd

                val parentTop = parent.paddingTop
                val parentBottom = bottom - top - parent.paddingBottom

                for (i in 0 until count) {
                    val child = parent.getChildAt(i)
                    when (i) {
                        0 -> {
                            child.layout(parentLeft, parentTop, parentLeft + child.measuredWidth, parentTop + child.measuredHeight)
                        }
                        1 -> {
                            child.layout(parentRight - child.measuredWidth, parentTop, parentRight, parentTop + child.measuredHeight)
                        }
                        else -> {
                            parent.defaultLayoutChild(child)
                        }
                    }
                }

            }
        }

        private class MeasurerTree : IMeasurer {
            override fun measureChild(parent: PoiCommentGridImgView,
                                      child: View,
                                      parentWidthMeasureSpec: Int,
                                      parentHeightMeasureSpec: Int,
                                      dataMgr: DataMgr<*>?) {
                val index = parent.indexOfChild(child)
                val parentChildWidth = MeasureSpec.getSize(parentWidthMeasureSpec) - (parent.paddingLeft + parent.paddingEnd)

                when (index) {
                    0 -> {
                        val width = (parentChildWidth - parent.dividerWidth) * 0.7
                        val widthSpec = MeasureSpec.makeMeasureSpec(width.toInt(), MeasureSpec.EXACTLY)
                        val heightSpec = MeasureSpec.makeMeasureSpec((width * 5 / 7).toInt(), MeasureSpec.EXACTLY)
                        child.measure(widthSpec, heightSpec)
                    }
                    1, 2 -> {
                        val width = (parentChildWidth - parent.dividerWidth) * 0.3
                        val widthSpec = MeasureSpec.makeMeasureSpec(width.toInt(), MeasureSpec.EXACTLY)
                        val heightSpec = MeasureSpec.makeMeasureSpec((width * 7 / 7).toInt(), MeasureSpec.EXACTLY)
                        child.measure(widthSpec, heightSpec)
                    }
                    else -> {
                        parent.superMeasureChildWithMargins(child, parentWidthMeasureSpec, 0, parentHeightMeasureSpec, 0)
                    }
                }
            }

            override fun layoutChildren(parent: PoiCommentGridImgView, left: Int, top: Int, right: Int, bottom: Int, forceLeftGravity: Boolean) {
                val count = parent.childCount
                val parentLeft = parent.paddingLeft
                val parentRight = right - left - parent.paddingEnd

                val parentTop = parent.paddingTop
                val parentBottom = bottom - top - parent.paddingBottom

                for (i in 0 until count) {
                    val child = parent.getChildAt(i)
                    when (i) {
                        0 -> {
                            child.layout(parentLeft, parentTop, parentLeft + child.measuredWidth, parentTop + child.measuredHeight)
                        }
                        1 -> {
                            child.layout(parentRight - child.measuredWidth, parentTop, parentRight, parentTop + child.measuredHeight)
                        }
                        2 -> {
                            child.layout(parentRight - child.measuredWidth, parentBottom - child.measuredHeight, parentRight, parentBottom)
                        }
                        else -> {
                            parent.defaultLayoutChild(child)
                        }
                    }
                }

            }
        }
    }

    class DataMgr<T>(val view: PoiCommentGridImgView,
                     val adapter: Adapter<T>) {
        init {
            view.dataMgr = this
        }

        val size: Int
            get() {
                return imgData.size
            }

        fun getUrl(index: Int): String {
            return adapter.getUrl(imgData[index])
        }

        fun getWidth(index: Int): Int {
            return adapter.getWidth(imgData[index])
        }

        fun getHeight(index: Int): Int {
            return adapter.getHeight(imgData[index])
        }

        fun showImgs(imgs: List<T>) {
            imgData.clear()
            imgData.addAll(imgs)
            view.showImg(this)
        }

        interface Adapter<T> {
            fun getUrl(t: T): String
            fun getWidth(t: T): Int
            fun getHeight(t: T): Int
        }

        private val imgData = mutableListOf<T>()

    }

    private val imgViews = listOf(
            ImageView(context),
            ImageView(context),
            ImageView(context)
    )

    private val dividerWidth = 10

    private var dataMgr: DataMgr<*>? = null

    interface Callback {
        fun onClickImg(pos: Int)
    }


    fun showImg(dataMgr: DataMgr<*>?) {
        val size = minOf(dataMgr?.size ?: 0, 3)
        measurer = when (size) {
            1 -> measurerOne
            2 -> measurerTwo
            3 -> measurerThree
            else -> measureDefault
        }
        refreshImgViews(size)

        for (i in 0 until size) {
            imgViews[i].backgroundColor = when (i) {
                0 -> Color.BLACK
                1 -> Color.BLUE
                2 -> Color.RED
                else -> Color.YELLOW
            }
        }

    }

    private fun refreshImgViews(size: Int) {
        removeAllViews()
        for (i in 0 until size) {
            addView(this.imgViews[i])
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    private var measurer: IMeasurer = measureDefault

    //<editor-fold desc="measure">
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (getChildAt(0) != null) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getChildAt(0).measuredHeight)
        }
    }

    override fun measureChildWithMargins(child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int) {
        measurer.measureChild(this, child, parentWidthMeasureSpec, parentHeightMeasureSpec, dataMgr)
    }

    private fun superMeasureChildWithMargins(child: View, parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int) {
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }
    //</editor-fold>

    //<editor-fold desc="layout">
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        layoutChildren(left, top, right, bottom, false /* no force left gravity */)
    }

    private fun defaultLayoutChild(child: View) {
        val parentLeft = paddingLeft
        val parentRight = right - left - paddingEnd

        val parentTop = paddingTop
        val parentBottom = bottom - top - paddingBottom
        child.layout(parentLeft, parentTop, parentLeft + child.measuredWidth, parentTop + child.measuredHeight)
    }

    private fun layoutChildren(left: Int, top: Int, right: Int, bottom: Int, forceLeftGravity: Boolean) {
        measurer.layoutChildren(this, left, top, right, bottom, forceLeftGravity)
    }
    //</editor-fold>

}
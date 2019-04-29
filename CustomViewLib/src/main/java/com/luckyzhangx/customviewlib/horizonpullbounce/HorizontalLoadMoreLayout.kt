package com.luckyzhangx.customviewlib.horizonpullbounce

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.Scroller
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.luckyzhangx.corejavalibs.core.configure
import com.luckyzhangx.corejavalibs.core.use
import org.jetbrains.anko.dip
import kotlin.math.abs

// Created by luckyzhangx on 2019-04-24.

private val Tag = "Horizontal"

abstract class ScrollListener {
    abstract fun scrolled(dx: Int)
}

open class HorizontalLoadMoreLayout
@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), NestedScrollingParent2 {

    private val vc = ViewConfiguration.get(context)

    var listener: ScrollListener? = null

    var enable: Boolean = true

    private val bounceCalculator = BoundCalculator()

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        if (scrollX > 0 && abs(velocityX) < 3 * vc.scaledMinimumFlingVelocity)
            return true
        return false
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (scrollX > 0 && dx < 0) {
            scrollX(dx)
            consumed[0] = dx
        }
    }

    var loadView: View? = null
        set(value) {
            field = value
            configure(field) {
                translationX = this@HorizontalLoadMoreLayout.width.toFloat()
            }
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        configure(loadView) load@{
            if (translationX == 0f)
                translationX = this@HorizontalLoadMoreLayout.width.toFloat()
            use(layoutParams) {
                width = (this@HorizontalLoadMoreLayout.scrollX + this@HorizontalLoadMoreLayout.width - this@load.x).toInt()
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        bounceCalculator.range = measuredWidth
    }

    private val aHandler = Handler()
    private val task = Runnable {
        reset()
    }

    private fun enqueReset() {
        deReset()
        aHandler.post(task)
    }

    private fun deReset() {
        aHandler.removeCallbacksAndMessages(null)
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        if (type == ViewCompat.TYPE_TOUCH)
            enqueReset()
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        if (!enable) return false
        if (axes == ViewCompat.SCROLL_AXIS_HORIZONTAL && type == ViewCompat.TYPE_TOUCH) {
            deReset()
            return true
        }
        return false
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        if (scrollX <= 0 && dxUnconsumed <= 0)
            return
        scrollX(dxUnconsumed)
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller.computeScrollOffset()) {
            Log.d(Tag, "computeScroll")
            scrollTo(scroller.currX, scroller.currY)
            invalidate()
            loadView?.requestLayout()
            listener?.scrolled(scrollX)
        }
    }

    private val scroller = Scroller(context)

    private fun reset() {
        scroller.startScroll(scrollX, scrollY, -scrollX, -scrollY)
        invalidate()
    }

    private fun scrollX(dx: Int) {
        bounceCalculator.scaledDistance = scrollX
        bounceCalculator.eatDistance(dx)
        val scrollTo = bounceCalculator.scaledDistance
        val currrentX = scrollX
        scrollTo(scrollTo, 0)
        loadView?.requestLayout()
        if (scrollX > dip(10)) {
            requestDisallowInterceptTouchEvent(true)
        }
        listener?.scrolled(scrollX)
    }
}

fun HorizontalLoadMoreLayout.attachToRecyclerView(rv: RecyclerView, endHolderOffset: Int) {
    rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val view = recyclerView.layoutManager?.findViewByPosition(recyclerView.adapter!!.itemCount - 1)
            use(view, { loadView?.translationX = this@attachToRecyclerView.width.toFloat() }) {
                val dx = right - (parent as View).width + endHolderOffset
                loadView?.translationX = this@attachToRecyclerView.width + minOf(0, dx).toFloat()
            }
        }
    })
}
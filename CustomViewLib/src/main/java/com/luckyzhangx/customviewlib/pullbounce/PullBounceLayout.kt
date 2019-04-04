package com.luckyzhangx.customviewlib.pullbounce

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.Scroller
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.ViewCompat

// Created by luckyzhangx on 2019/3/28.

/**
 * todo scroll up 时越界回弹（因为 fling 速度由大变小，回弹效果很奇怪，暂未实现）
 * 坑：触发 scroll 时，因为onIntercept 和 onTouch 都会触发滑动，所以开始滚动时就会收到 onStopNestedScroll 事件，导致回弹的时机有冲突。
 */
class PullBounceLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        style: Int = 0
) : FrameLayout(
        context, attrs, style
), NestedScrollingParent2 {

    val topOffset = 96

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (dy < 0) {
            return
        }
        val scrolled = scroll(dy)
        if (scrollY < topOffset) consumed[1] = dy
        else consumed[1] = scrolled
    }

    override fun onStopNestedScroll(target: View, type: Int) {
//        if (scrollY < 0) {
//            scrollBy(0, -scrollY)
//        }
        reset()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
        }
        stopReset()
        val handle = super.dispatchTouchEvent(ev)
//        if (ev?.action == MotionEvent.ACTION_UP || ev?.action == MotionEvent.ACTION_CANCEL)
//            reset()
        return handle
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        stopReset()

        return true
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        scroll(dyUnconsumed, type)
    }

    private val scroller = Scroller(context)

    private fun stopReset() {
        if (scroller.computeScrollOffset()) {
            scroller.forceFinished(true)
        }
    }

    private fun reset() {
        if (scrollY >= 0) return
        scroller.startScroll(0, scrollY, 0, -scrollY)
        invalidate()
    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller.computeScrollOffset()) {
            scrollToWithListener(scroller.currY)
        }
    }

    private fun scroll(dx: Int, type: Int = ViewCompat.TYPE_TOUCH): Int {
        val cY = scrollY
        val currentX = convertYtoX(scrollY)
        var toY = convertXToY(currentX + dx)
//        fling 回弹效果不理想，关闭 fling 回弹
        if (type != ViewCompat.TYPE_TOUCH && toY < 0) {
            toY = 0
        }
        if (toY >= topOffset) {
            toY = topOffset
        }

        val toX = convertYtoX(toY)

        scrollToWithListener(toY)
        val scrolled = toX - currentX
        return scrolled

    }

    private val DRAG_MAX_HEIGHT = 1080
    private val dragFactor = -DRAG_MAX_HEIGHT * DRAG_MAX_HEIGHT

    private fun convertXToY(x: Int): Int {
        var result = 0
        if (x >= 0 && x <= topOffset) result = x
        if (x < 0) {
            result = -(dragFactor / (Math.abs(x) + DRAG_MAX_HEIGHT) + DRAG_MAX_HEIGHT)
        }
        if (x > topOffset) {
            result = topOffset
        }
        return result
    }

    private fun convertYtoX(y: Int): Int {
        var result = 0
        if (y >= 0 && y <= topOffset) result = y
        if (y < 0) {
            result = -((DRAG_MAX_HEIGHT * -y) / (DRAG_MAX_HEIGHT + y))
        }
        if (y > topOffset) {
            result = y
        }
        return result
    }

    private fun scrollToWithListener(y: Int) {
        scrollTo(0, y)
        invalidate()
        listener?.onScrolled(y)
    }

    var listener: ScrollListener? = null


    interface ScrollListener {
        fun onScrolled(scrolledY: Int)
    }
}
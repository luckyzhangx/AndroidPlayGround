package com.luckyzhangx.customviewdemo.recyclerview

import android.graphics.Rect
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.luckyzhangx.corejavalibs.core.use
import kotlin.math.abs

abstract class RecyclerViewPageListener : RecyclerView.OnScrollListener() {

    private var lastPos = -1
    private val checkTask = Runnable {
        use(rv) {
            checkpage(this)
        }
    }
    private val handler = Handler()

    private var orientationHelper: OrientationHelper? = null
    private var rv: RecyclerView? = null

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        rv = recyclerView
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            deqCheckTask()
            enqCheckTask()
        } else {
            deqCheckTask()
        }
    }

    private fun enqCheckTask() {
        handler.postDelayed(checkTask, 32)
    }

    private fun deqCheckTask() {
        handler.removeCallbacks(checkTask)
    }

    private fun checkpage(recyclerView: RecyclerView) {
        ensureOrientationHelper(recyclerView)
        val view = findCenterView(recyclerView.layoutManager!!, orientationHelper!!)
        if (view?.isHorizontalFullVisible() == true) {
            val pos = recyclerView.getChildViewHolder(view).adapterPosition
            if (lastPos != pos) {
                onPageSel(recyclerView, pos)
                lastPos = pos
            }
        }
    }

    private fun ensureOrientationHelper(recyclerView: RecyclerView) {
        if (orientationHelper == null) {
            orientationHelper = OrientationHelper.createHorizontalHelper(recyclerView.layoutManager)
        }
    }

    abstract fun onPageSel(recyclerView: RecyclerView, pos: Int)
}

private fun findCenterView(layoutManager: RecyclerView.LayoutManager,
                           helper: OrientationHelper): View? {
    val childCount = layoutManager.childCount
    if (childCount == 0) {
        return null
    }

    var closestChild: View? = null
    val center: Int
    if (layoutManager.clipToPadding) {
        center = helper.startAfterPadding + helper.totalSpace / 2
    } else {
        center = helper.end / 2
    }
    var absClosest = Integer.MAX_VALUE

    for (i in 0 until childCount) {
        val child = layoutManager.getChildAt(i)
        val childCenter = helper.getDecoratedStart(child) + helper.getDecoratedMeasurement(child) / 2
        val absDistance = Math.abs(childCenter - center)

        /** if child center is closer than previous closest, set it as closest   */
        if (absDistance < absClosest) {
            absClosest = absDistance
            closestChild = child
        }
    }
    return closestChild
}

private fun View.isHorizontalFullVisible(): Boolean {
    val rect = Rect()
    val partVisible = getLocalVisibleRect(rect)
    val viewInWindow = partVisible && (abs(rect.width() - width) <= 2)
    return isShown && viewInWindow
}


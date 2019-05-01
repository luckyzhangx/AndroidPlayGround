package com.luckyzhangx.customviewlib.infiniteadapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckyzhangx.coreandroidlibs.recyclerview.smoothScrollToPositionTop
import com.luckyzhangx.coreandroidlibs.recyclerview.tryFindFirstVisiblePosition

// Created by luckyzhangx on 2019-04-29.
class InfiniteAdapter<VH : RecyclerView.ViewHolder>(
        val adapter: RecyclerView.Adapter<VH>
) : RecyclerView.Adapter<VH>() {
    private fun Int.toRealPos(): Int {
        val size = adapter.itemCount
        if (size == 0) return 0
        else return this % (size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return adapter.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        adapter.onBindViewHolder(holder, position)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}

private fun RecyclerView.scrollToNext() {
    val current = layoutManager?.tryFindFirstVisiblePosition() ?: -1
    if (current < 0) return
    val itemCount = adapter?.itemCount ?: -1
    if (itemCount <= 0) return

    val next = (current + 1) % itemCount
    smoothScrollToPositionTop(next)
}

fun RecyclerView.startCarousel(duration: Long) {
    if (adapter != null)
        adapter = InfiniteAdapter(adapter!!)

    val task = Runnable {
        if (!isAttachedToWindow) return@Runnable
        scrollToNext()
    }

    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                recyclerView.postDelayed(task, duration)
            }
        }
    })

    addOnAttachStateChangeListener(
            object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View?) {
                    removeCallbacks(task)
                }

                override fun onViewAttachedToWindow(v: View?) {
                    if (adapter != null && adapter !is InfiniteAdapter)
                        adapter = InfiniteAdapter(adapter!!)
                    postDelayed(task, duration)
                }
            })
}
package com.example.pagerheaderdemo

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

// Created by luckyzhangx on 2018/10/20.
/**
 * 只适用于 LinearLayoutManager，GridLayoutManager 或 StaggeredGridLayoutManager
 */
fun RecyclerView.LayoutManager.isAtBottom(): Boolean {
    return findLastCompletelyVisiblePosition() == itemCount - 1
}

fun RecyclerView.LayoutManager.findLastVisiblePosition(): Int {
    return when (this) {
        is LinearLayoutManager ->
            findLastVisibleItemPosition()
        is StaggeredGridLayoutManager -> {
            val poses = IntArray(spanCount)
            findLastVisibleItemPositions(poses).max() ?: RecyclerView.NO_POSITION
        }
        else -> RecyclerView.NO_POSITION
    }
}

fun RecyclerView.LayoutManager.findLastCompletelyVisiblePosition(): Int {
    return when (this) {
        is LinearLayoutManager ->
            findLastCompletelyVisibleItemPosition()
        is StaggeredGridLayoutManager -> {
            val poses = IntArray(spanCount)
            findLastCompletelyVisibleItemPositions(poses).max() ?: RecyclerView.NO_POSITION
        }
        else -> RecyclerView.NO_POSITION
    }
}

fun RecyclerView.LayoutManager.tryFindFirstVisiblePosition(): Int {
    return when (this) {
        is LinearLayoutManager -> {
            findFirstVisibleItemPosition()
        }
        is StaggeredGridLayoutManager -> {
            val poses = IntArray(spanCount)
            findFirstVisibleItemPositions(poses).max() ?: RecyclerView.NO_POSITION
        }
        else -> RecyclerView.NO_POSITION
    }
}

fun RecyclerView.LayoutManager.tryFindFirstCompletelyVisiblePosition(): Int {
    return when (this) {
        is LinearLayoutManager -> {
            findFirstCompletelyVisibleItemPosition()
        }
        is StaggeredGridLayoutManager -> {
            val poses = IntArray(spanCount)
            findFirstCompletelyVisibleItemPositions(poses).max() ?: RecyclerView.NO_POSITION
        }
        else -> RecyclerView.NO_POSITION
    }
}

fun RecyclerView.smoothScrollToPositionTop(pos: Int) {
    if (pos == RecyclerView.NO_POSITION) return
    val lm = layoutManager
    if (lm is LinearLayoutManager) {
        val vPos = lm.findFirstVisibleItemPosition()
        val cPos = lm.findFirstCompletelyVisibleItemPosition()
        if (vPos == cPos && cPos == pos) return
    }
    val scroller = object : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int {
            return LinearSmoothScroller.SNAP_TO_START
        }
    }
    scroller.targetPosition = pos

    layoutManager?.startSmoothScroll(scroller)
}

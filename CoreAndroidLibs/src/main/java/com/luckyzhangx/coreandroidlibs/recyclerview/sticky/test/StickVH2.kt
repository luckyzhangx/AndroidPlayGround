package com.luckyzhangx.coreandroidlibs.recyclerview.sticky.test

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.luckyzhangx.coreandroidlibs.recyclerview.sticky.StickViewInner
import com.luckyzhangx.coreandroidlibs.recyclerview.sticky.StickyMgr
import com.luckyzhangx.coreandroidlibs.recyclerview.sticky.stickMgr
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.BasicVH
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.GenericViewRenderer
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.debug
import org.jetbrains.anko.padding

// Created by luckyzhangx on 2019-09-16.
class StickRenderer2 : GenericViewRenderer<StickVH2>

class StickVH2(parent: ViewGroup) : BasicVH<StickRenderer2>(StickViewInner(parent.context)), AnkoLogger {

    private var stickViewMgr: StickyMgr? = parent.stickMgr

    override fun onBind(data: StickRenderer2, pos: Int) {
        val v = stickViewMgr?.getView(data, pos) ?: createView().apply {
            (itemView as ViewGroup).addView(this)
        }
        (itemView as StickViewInner).stickyView = v
        //bind v
    }

    private fun createView(): View? {
        debug("create sticky")
        return StickyFrameLayout(itemView.context).apply {
            padding = 30
            backgroundColor = Color.BLUE
            addView(TextView(itemView.context).apply {
                text = "sticky@${this.hashCode()}"
            })
        }
    }
}
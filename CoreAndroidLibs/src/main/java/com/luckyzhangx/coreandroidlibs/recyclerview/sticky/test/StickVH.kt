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
class StickRenderer : GenericViewRenderer<StickVH>

class StickVH(parent: ViewGroup) : BasicVH<StickRenderer>(StickViewInner(parent.context)), AnkoLogger {

    private var stickViewMgr: StickyMgr? = parent.stickMgr

    override fun onBind(data: StickRenderer, pos: Int) {
        val v = if (stickViewMgr?.hasView(data, pos) == true) {
            stickViewMgr?.getView(data, pos)
        } else {
            //create v?
            createView().apply {
                (itemView as ViewGroup).addView(this)
            }
        }
        (itemView as StickViewInner).stickyView = v
        //bind v
    }

    private fun createView(): View? {
        debug("creat sticky")
        return StickyFrameLayout(itemView.context).apply {
            padding = 30
            backgroundColor = Color.RED
            addView(TextView(itemView.context).apply {
                text = "sticky@${this.hashCode()}"
            })
        }
    }
}
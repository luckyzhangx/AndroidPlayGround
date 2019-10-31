package com.luckyzhangx.coreandroidlibs.recyclerview.sticky.test

import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.BasicVH
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.GenericViewRenderer
import org.jetbrains.anko.padding

// Created by luckyzhangx on 2019-09-16.
class NoStickRenderer : GenericViewRenderer<NoStickVH>

class NoStickVH(parent: ViewGroup) : BasicVH<NoStickRenderer>(FrameLayout(parent.context).apply {
    padding = 30
    addView(TextView(parent.context).apply {
        text = "no sticky"
    })
}) {

    override fun onBind(data: NoStickRenderer, pos: Int) {
    }
}
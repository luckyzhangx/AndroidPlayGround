package com.luckyzhangx.coreandroidlibs.utils.renderer

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.BasicVH
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.GenericViewRenderer
import org.jetbrains.anko.backgroundColor

class TextViewRenderer(val content: String) : GenericViewRenderer<TextViewVH>

class TextViewVH(context: Context) : BasicVH<TextViewRenderer>(TextView(context).apply {
    layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    textSize = 32f
}) {
    companion object {
        val colors = listOf(Color.RED, Color.BLUE, Color.GREEN)
    }

    override fun onBind(data: TextViewRenderer, pos: Int) {
        (itemView as TextView).text = data.content
        itemView.backgroundColor = colors.random()
    }
}
package com.luckyzhangx.coreandroidlibs.renderer.renderadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

object InternalViewRenderUtil {
    fun getViewRenderType(renderer: Any): Int {
        return if (renderer is ViewRenderer<*>) {
            renderer.getViewHolderType()
        } else {
            InternalViewRendererImpl.getViewHolderType(renderer)
        }
    }

    @Deprecated("没有办法的办法，暂时就这样吧")
    fun getViewRenderType(clazz: Class<*>): Int {
        return clazz.hashCode()
    }

    fun <T : RecyclerView.ViewHolder> createViewHolder(renderer: Any, container: ViewGroup)
            : T {
        return if (renderer is ViewRenderer<*>) {
            renderer.createViewHolder(container.context, container) as T
        } else {
            InternalViewRendererImpl.createViewHolder(renderer, container.context, container)
        }
    }
}
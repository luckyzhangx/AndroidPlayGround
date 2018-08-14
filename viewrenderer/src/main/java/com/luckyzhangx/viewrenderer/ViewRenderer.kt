package com.luckyzhangx.viewrenderer

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlin.reflect.KClass

interface ViewRenderer<Holder : RecyclerView.ViewHolder> {
    fun getViewType(): Int = ViewRenderer::class.hashCode()

    fun getViewHolderType(): KClass<*>

    fun createViewHolder(context: Context, container: ViewGroup?): RecyclerView.ViewHolder {
        getViewHolderType().constructors.forEach {
            if ((it.parameters.size == 2
                            && it.parameters[0] is Context
                            && it.parameters[1] is ViewGroup)) {
                return (it.call(context, container).takeIf { viewHolder ->
                    viewHolder is RecyclerView.ViewHolder
                } ?: throw IllegalStateException()) as RecyclerView.ViewHolder
            }
        }
        throw IllegalStateException()
    }


}

object ViewRendererManager {
    val rendererMap = mutableMapOf<Int, KClass<*>>(1 to Any::class)
}
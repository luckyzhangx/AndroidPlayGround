package com.luckyzhangx.coreandroidlibs.renderer.renderadapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// Created by luckyzhangx on 2018/8/14.

interface GenericViewRenderer<T : RecyclerView.ViewHolder> : ViewRenderer<T> {
    override fun getViewHolderType() = InternalViewRendererImpl.getViewHolderType(this)

    override fun createViewHolder(context: Context, container: ViewGroup?): T {
        return InternalViewRendererImpl.createViewHolder(this, context, container)
    }
}

/**
 * java might need this
 */

interface ViewRenderer<T : RecyclerView.ViewHolder> {
    fun getViewHolderType(): Int

    fun createViewHolder(context: Context, container: ViewGroup?): T

}


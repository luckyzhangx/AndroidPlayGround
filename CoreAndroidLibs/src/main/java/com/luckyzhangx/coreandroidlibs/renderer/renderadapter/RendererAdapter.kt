package com.luckyzhangx.coreandroidlibs.renderer.renderadapter

import android.content.Context
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

// Created by luckyzhangx on 2018/8/31.
abstract class BaseViewRendererAdapter<T : RecyclerView.ViewHolder>
    : RecyclerView.Adapter<T>() {

    private val delegate = RendererAdapterDelegate<T>(object : ItemCallback {
        override fun getItem(pos: Int): Any? {
            return this@BaseViewRendererAdapter.getItem(pos)
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return (delegate.createViewHolder(parent, viewType) as T?)
                ?: throw IllegalStateException("can't initiate proper type ViewHolder")
    }

    @CallSuper
    override fun getItemViewType(position: Int): Int {
        return delegate.getViewHolderType(position)
    }

    abstract fun getItem(poi: Int): Any?
}

open class DiffViewRendererAdapter : BaseViewRendererAdapter<BasicVH<Any>>() {

    private val list = mutableListOf<Any>()

    override fun getItem(poi: Int): Any? {
        return list[poi]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BasicVH<Any>, position: Int) {
        holder.onBind(list.get(position), position)
    }

    fun postList(newList: List<Any>) {
        val old = list.toList()
        list.clear()
        list.addAll(newList)
        DiffUtil.calculateDiff(EqualCheckDiffCallback(old, list)).dispatchUpdatesTo(this)
    }
}

class SingleSelDiffViewRendererAdapter : DiffViewRendererAdapter() {
    var sel: Int = -1
        set(value) {
            if (isIndexValid(value)) {
                if (isIndexValid(field)) {
                    (getItem(field) as? SelableRenderer)?.seled = false
                    notifyItemChanged(field)
                }

                (getItem(value) as? SelableRenderer)?.seled = true
                field = value
                notifyItemChanged(field)

            }
        }

    private fun isIndexValid(index: Int): Boolean {
        return index in 0 until itemCount
    }
}

interface SelableRenderer {
    var seled: Boolean
}
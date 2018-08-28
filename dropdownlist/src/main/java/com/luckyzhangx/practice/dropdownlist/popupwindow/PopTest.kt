package com.luckyzhangx.practice.dropdownlist.popupwindow

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.luckyzhangx.practice.dropdownlist.R

// Created by luckyzhangx on 2018/8/27.

typealias OnSelListener = (pos: Int) -> Unit

open class TestSingleSelAdapterDelegate : AdapterDelegate<DefaultViewHolder> {


    private var _sel = 0
    var sel: Int
        set(value) {
            _sel = value
            adapter?.notifyDataSetChanged()
            selListeners.forEach { it.invoke(sel) }
        }
        get() {
            return _sel
        }

    private val selListeners = mutableListOf<OnSelListener>()

    fun addSelListener(listener: OnSelListener) {
        selListeners.add(listener)
    }

    var count = 10
        set(value) {
            _sel = -1
            field = value
            adapter?.notifyDataSetChanged()
        }

    private var adapter: RecyclerView.Adapter<*>? = null

    fun attach(adapter: RecyclerView.Adapter<*>) {
        this.adapter = adapter
    }

    override fun createViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        return DefaultViewHolder(parent.context, parent)
    }

    override fun getItemCount(): Int {
        return count
    }

    override fun bindViewHolder(holder: DefaultViewHolder, position: Int) {
        if (position == sel) {
            holder.sel()
        } else
            holder.unsel()
    }

    override fun onItemClick(vh: DefaultViewHolder, position: Int) {
        sel = position

        Toast.makeText(vh.itemView.context, "item $position clicked", Toast.LENGTH_SHORT).show()
    }

    override fun renderSel(holder: DefaultViewHolder) {
        holder.sel()
    }

    override fun renderUnSel(holder: DefaultViewHolder) {
        holder.unsel()
    }

    override fun checkSel(position: Int): Boolean {
        return position == sel
    }
}

class MainTestAdapterDelegate : TestSingleSelAdapterDelegate(), MainAdapterDelegate<DefaultViewHolder> {

    private val subDelegate = TestSingleSelAdapterDelegate()

    private var preSel = -1
    private var preSubSel = -1

    init {
        subDelegate.addSelListener {
            preSel = sel
            preSubSel = it
        }
    }

    override fun createViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        return DefaultMainViewHolder(parent.context, parent)
    }

    override fun onItemClick(vh: DefaultViewHolder, position: Int) {
        super.onItemClick(vh, position)
        subDelegate.count = 2 * position
        if (position == preSel) {
            subDelegate.sel = preSubSel
        }
    }

    override fun getSubDelegate(): AdapterDelegate<*> {
        return subDelegate
    }
}


open class DefaultViewHolder(context: Context, container: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_filter, container, false)) {
    val tv: TextView
        get() {
            return itemView.findViewById(R.id.tv)
        }

    open fun sel() {
        tv.setTypeface(null, Typeface.BOLD)
    }

    open fun unsel() {
        tv.setTypeface(null, Typeface.NORMAL)
    }
}

class DefaultMainViewHolder(context: Context, container: ViewGroup)
    : DefaultViewHolder(context, container) {

    override fun sel() {
        itemView.setBackgroundColor(Color.RED)
    }

    override fun unsel() {
        itemView.setBackgroundColor(Color.WHITE)
    }
}
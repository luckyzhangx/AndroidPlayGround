package com.luckyzhangx.practice.dropdownlist.popupwindow

import android.view.ViewGroup

// Created by luckyzhangx on 2018/8/29.

abstract class BaseSelWrapperDelegate : SelWrapperDelegate<SelWrapperViewHolder, SelViewHolder> {


    override fun createWrapperViewHolder(parent: ViewGroup, viewType: Int): SelWrapperViewHolder {
        return SelWrapperViewHolder(parent.context, parent)
    }

    override fun bindWrapperViewHolder(holder: SelWrapperViewHolder, position: Int) {
        holder.bind(this)
    }

    private var sels = mutableSetOf<Int>()

    override fun renderSel(holder: SelViewHolder) {
        holder.sel()
    }

    override fun renderUnSel(holder: SelViewHolder) {
        holder.unsel()
    }

    override fun getTitle(): String {
        return "筛选标题"
    }

    override fun createViewHolder(parent: ViewGroup, viewType: Int): SelViewHolder {
        return SelViewHolder(parent.context, parent)
    }

    override fun isSel(pos: Int): Boolean {
        return sels.contains(pos)
    }

    override fun updateSel(pos: Int) {
        if (isSel(pos)) {
            sels.remove(pos)
        } else {
            sels.add(pos)
        }
    }

    override fun getSels(): Set<Int> {
        return sels
    }
}
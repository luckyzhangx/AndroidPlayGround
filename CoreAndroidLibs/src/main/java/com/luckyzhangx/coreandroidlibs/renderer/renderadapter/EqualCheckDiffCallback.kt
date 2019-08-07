package com.luckyzhangx.coreandroidlibs.renderer.renderadapter

import androidx.recyclerview.widget.DiffUtil

// Created by luckyzhangx on 2018/12/12.

class EqualCheckDiffCallback(val oldList: List<out Any>, val newList: List<out Any>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(oldItemPosition, newItemPosition)
    }
}
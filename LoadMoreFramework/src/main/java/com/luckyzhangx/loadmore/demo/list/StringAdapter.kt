package com.luckyzhangx.loadmore.demo.list

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class StringAdapter : RecyclerView.Adapter<StringVH>() {

    private val list = mutableListOf<String>()

    fun postList(newlist: List<String>) {
        val old = list.toList()
        list.clear()
        list.addAll(newlist)
        DiffUtil.calculateDiff(Callback(old, list)).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringVH {
        return StringVH(parent)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: StringVH, position: Int) {
        holder.bindString(list[position])
    }

    private class Callback(val old: List<String>, val new: List<String>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }

        override fun getOldListSize(): Int {
            return old.size
        }

        override fun getNewListSize(): Int {
            return new.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areItemsTheSame(oldItemPosition, newItemPosition)
        }
    }
}
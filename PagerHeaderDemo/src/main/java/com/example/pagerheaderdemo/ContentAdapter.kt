package com.example.pagerheaderdemo

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

// Created by luckyzhangx on 2018/12/17.
class ContentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        val TYPE_SIMPLE_VH = 1
        val TYPE_PAGER = 2
    }

    val list = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SIMPLE_VH -> SimpleVH(parent)
            TYPE_PAGER -> PagerVH(parent)
            else -> throw IllegalStateException()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is Simple -> TYPE_SIMPLE_VH
            is Page -> TYPE_PAGER
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }
}
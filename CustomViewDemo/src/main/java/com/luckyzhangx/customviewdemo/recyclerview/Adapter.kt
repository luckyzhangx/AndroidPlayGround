package com.luckyzhangx.customviewdemo.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// Created by luckyzhangx on 2019-04-24.
class Adapter(size: Int) : RecyclerView.Adapter<Holder>() {
    private val list = mutableListOf<String>()

    init {
        repeat(size) {
            list.add("holder")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(parent)


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
    }
}
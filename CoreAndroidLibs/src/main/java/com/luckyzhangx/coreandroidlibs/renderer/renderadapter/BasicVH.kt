package com.luckyzhangx.coreandroidlibs.renderer.renderadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// Created by luckyzhangx on 2019-07-31.
abstract class BasicVH<in T> : RecyclerView.ViewHolder {

    constructor(parent: ViewGroup, layoutId: Int) : super(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    constructor(context: Context, layoutId: Int) : super(LayoutInflater.from(context).inflate(layoutId, null))

    abstract fun onBind(data: T, pos: Int)
}
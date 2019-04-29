package com.luckyzhangx.customviewdemo.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckyzhangx.customviewdemo.R

// Created by luckyzhangx on 2019-04-24.
class Holder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.holder, parent, false)
)
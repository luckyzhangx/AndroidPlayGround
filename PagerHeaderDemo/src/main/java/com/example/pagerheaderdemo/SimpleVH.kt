package com.example.pagerheaderdemo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

// Created by luckyzhangx on 2018/12/17.
class SimpleVH(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.simple_vh, parent, false))

class Simple
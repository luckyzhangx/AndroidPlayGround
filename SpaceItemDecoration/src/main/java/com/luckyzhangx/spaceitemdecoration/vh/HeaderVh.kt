package com.luckyzhangx.spaceitemdecoration.vh

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.luckyzhangx.spaceitemdecoration.R

class HeaderVH(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.vh_header, parent, false))

class Header

class AnchorVH(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.vh_content_anchor, parent, false))

class Anchor

class AnchorEndVH(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.vh_content_anchor, parent, false))

class AnchorEnd

class ContentVH(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context)
        .inflate(R.layout.vh_cotent, parent, false))

class Content
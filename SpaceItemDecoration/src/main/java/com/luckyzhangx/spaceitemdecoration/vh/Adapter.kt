package com.luckyzhangx.spaceitemdecoration.vh

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import java.lang.IllegalStateException

class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Header::class.java.hashCode() -> HeaderVH(parent)
            Anchor::class.java.hashCode() -> AnchorVH(parent)
            AnchorEnd::class.java.hashCode() -> AnchorEndVH(parent)
            Content::class.java.hashCode() -> ContentVH(parent)
            else -> throw IllegalStateException("")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position]::class.java.hashCode()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
}